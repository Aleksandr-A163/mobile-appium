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
        APP_DOWNLOAD_URL = 'http://host.docker.internal:8089/download/wishlist.apk'
        APPIUM_HOST = 'http://host.docker.internal'
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
                  BOOT_OK=0
                  for i in $(seq 1 120); do
                    BOOT_STATUS=$(docker exec android-emulator adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\\r' || true)
                    if [ "$BOOT_STATUS" = "1" ]; then
                      BOOT_OK=1
                      break
                    fi
                    sleep 5
                  done

                  if [ "$BOOT_OK" -ne 1 ]; then
                    echo "Emulator boot did not complete"
                    docker compose logs --no-color || true
                    exit 1
                  fi

                  echo "Waiting for Appium..."
                  APP_OK=0
                  for i in $(seq 1 60); do
                    if curl -fsS "${APPIUM_HOST}:4723/status" >/dev/null; then
                      APP_OK=1
                      break
                    fi
                    sleep 5
                  done

                  if [ "$APP_OK" -ne 1 ]; then
                    echo "Appium did not become ready"
                    docker compose logs --no-color || true
                    exit 1
                  fi
                '''
            }
        }

        stage('Install APK') {
            steps {
                echo 'Skipping manual APK install; tests will use app.download.url'
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
                    -Dapp.download.url="${APP_DOWNLOAD_URL}" \
                    -Dappium.host="${APPIUM_HOST}"
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