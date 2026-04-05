pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    parameters {
        string(
            name: 'APK_URL',
            defaultValue: 'https://raw.githubusercontent.com/Aleksandr-A163/mobile-appium/main/wiremock/__files/wishlist.apk',
            description: 'Direct URL to APK file'
        )
        string(
            name: 'DB_USERNAME',
            defaultValue: 'student',
            description: 'Database username'
        )
        password(
            name: 'DB_PASSWORD',
            defaultValue: 'student',
            description: 'Database password'
        )
    }

    environment {
        APP_DOWNLOAD_URL = 'http://wiremock:8080/download/wishlist.apk'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare APK') {
            steps {
                sh '''
                  set -eux
                  mkdir -p wiremock/__files
                  curl -L "${APK_URL}" -o wiremock/__files/wishlist.apk
                  ls -lah wiremock/__files
                '''
            }
        }

        stage('Start mobile infrastructure') {
            steps {
                sh '''
                  set -eux
                  docker compose down || true
                  docker compose up -d
                  docker compose ps
                '''
            }
        }

        stage('Wait for emulator and Appium') {
            steps {
                sh '''
                  set -eux

                  echo "Waiting for Android container..."
                  for i in $(seq 1 60); do
                    if docker ps --format '{{.Names}}' | grep -q '^android-emulator$'; then
                      break
                    fi
                    sleep 5
                  done

                  echo "Waiting for emulator device..."
                  for i in $(seq 1 120); do
                    if docker exec android-emulator adb devices | grep -q "emulator-5554[[:space:]]*device"; then
                      break
                    fi
                    sleep 5
                  done

                  echo "Waiting for boot completion..."
                  for i in $(seq 1 120); do
                    BOOT_STATUS=$(docker exec android-emulator adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r' || true)
                    if [ "$BOOT_STATUS" = "1" ]; then
                      break
                    fi
                    sleep 5
                  done

                  echo "Waiting for Appium..."
                  for i in $(seq 1 60); do
                    if curl -s http://localhost:4723/wd/hub/status >/dev/null; then
                      break
                    fi
                    sleep 5
                  done
                '''
            }
        }

        stage('Install APK') {
            steps {
                sh '''
                  set -eux
                  docker exec android-emulator adb install -r /root/tmp/apk/wishlist.apk || true
                '''
            }
        }

        stage('Run tests') {
            steps {
                sh '''
                  set -eux
                  chmod +x gradlew
                  ./gradlew clean test \
                    -DdatabaseUsername="${DB_USERNAME}" \
                    -DdatabasePassword="${DB_PASSWORD}" \
                    -Dapp.download.url="${APP_DOWNLOAD_URL}"
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'build/reports/**, build/allure-results/**', allowEmptyArchive: true

            allure([
                includeProperties: false,
                results: [[path: 'build/allure-results']]
            ])

            sh '''
              docker compose logs --no-color > docker-compose.log || true
              docker compose down || true
            '''

            archiveArtifacts artifacts: 'docker-compose.log', allowEmptyArchive: true
        }
    }
}