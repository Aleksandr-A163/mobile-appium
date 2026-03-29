# Mobile Appium Homework

Проект с UI-тестами мобильного приложения на Java с использованием Appium, Selenide, JUnit 5 и Guice.

## Стек

- Java 17
- Gradle
- JUnit 5
- Selenide
- Selenide Appium
- Appium Java Client
- Google Guice
- PostgreSQL JDBC
- Docker Compose
- WireMock

## Подготовка APK

Перед запуском положите файл `wishlist.apk` в каталог:

```bash
wiremock/__files/wishlist.apk
```

После этого APK будет доступен по адресу:

```text
http://127.0.0.1:8089/download/wishlist.apk
```

## Запуск инфраструктуры

```bash
docker compose up -d
```

После запуска будут доступны:

- Appium: `http://127.0.0.1:4723`
- WireMock: `http://127.0.0.1:8089`
- VNC эмулятора: `http://127.0.0.1:6080`

## Запуск тестов

### Linux / macOS

```bash
./gradlew clean test \
  -DdatabaseUsername=student \
  -DdatabasePassword=student
```

### Windows

```powershell
.\gradlew.bat clean test `
  -DdatabaseUsername=student `
  -DdatabasePassword=student
```

### Запуск отдельного теста

```bash
./gradlew test --tests "ru.otus.mobile.tests.GiftTest.shouldFixGiftPrice" \
  -DdatabaseUsername=student \
  -DdatabasePassword=student
```

## Тестовые сценарии

- создание списка желаний
- редактирование списка желаний
- создание и редактирование подарка
- изменение цены подарка

## Подготовка данных

Перед выполнением тестов состояние в БД подготавливается автоматически через `TestDataPreparer` и `TestDataScenario`.

