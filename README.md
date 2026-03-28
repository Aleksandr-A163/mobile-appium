# Mobile Appium Homework — Refactored

Упрощённый и переработанный проект для домашнего задания по мобильной автоматизации.

## Что изменено

- удалён SQL-mapping через файлы и аннотации
- удалён `BaseTest`
- сохранены **JUnit 5 extensions** и **Guice**
- подготовка данных выполняется первой строкой теста через JDBC utility
- убраны низкоуровневые вызовы ADB с хоста
- из `docker-compose` убран `privileged: true`
- APK больше не монтируется в контейнер, а скачивается через **WireMock** по URL из capabilities
- capabilities переведены на `UiAutomator2Options`
- добавлен `BlockingQueue` для поддержки нескольких эмуляторов
- добавлена базовая иерархия `AbsPageObject -> AbsBasePage / BaseMobileComponent`
- сохранен `spotlessApply` при компиляции

## Стек

- Java 17
- Gradle
- JUnit 5
- Selenide + Selenide Appium
- Appium Java Client
- Google Guice
- Docker Compose
- WireMock
- PostgreSQL JDBC
- Spotless

## Структура проекта

```text
mobile-appium-homework/
  build.gradle
  settings.gradle
  gradle.properties
  docker-compose.yml
  README.md
  .gitignore
  gradle/
    libs.versions.toml
  wiremock/
    mappings/
      app-download.json
    __files/
      wishlist.apk               # кладётся вручную перед запуском
  src/
    test/
      java/
        ru/otus/mobile/
          components/
          config/
          data/
          di/
          driver/
          extensions/
          pages/
          support/
          tests/
      resources/
        application.properties
        test-users.properties
```

## Что было удалено из исходной архитектуры

- `BaseTest`
- SQL-файлы и файловый mapping на SQL
- низкоуровневые ADB-команды на хосте
- зависимость от `adb.path`
- volume mount APK в контейнер с эмулятором
- привилегированный режим контейнера

## Подготовка APK

Перед запуском положите реальный `wishlist.apk` в каталог:

```bash
wiremock/__files/wishlist.apk
```

После этого файл будет доступен по URL:

```text
http://127.0.0.1:8089/download/wishlist.apk
```

Этот URL передаётся в capabilities через свойство `app.download.url`.

## Запуск инфраструктуры

```bash
docker compose up -d
```

После запуска:

- Appium будет доступен по `http://127.0.0.1:4723`
- WireMock будет доступен по `http://127.0.0.1:8089`
- VNC UI эмулятора будет доступен по `http://127.0.0.1:6080`

## Запуск тестов

### Linux / macOS

```bash
./gradlew clean test   -DdatabaseUsername=student   -DdatabasePassword=student
```

### Windows

```powershell
.\gradlew.bat clean test `
  -DdatabaseUsername=student `
  -DdatabasePassword=student
```

### При необходимости можно переопределить адреса

```bash
./gradlew clean test   -DdatabaseUrl=jdbc:postgresql://sql.otus.kartushin.su:5432/wishlist   -DdatabaseUsername=student   -DdatabasePassword=student   -Dappium.url=http://127.0.0.1:4723   -Dapp.download.url=http://127.0.0.1:8089/download/wishlist.apk
```

## Тестовые сценарии

- создание списка желаний
- редактирование списка желаний
- создание и редактирование подарка
- изменение статуса резервирования подарка другого пользователя

## Важно про подготовку данных

Подготовка данных теперь выполняется **в коде**, а не через SQL-файлы в classpath.

Первая строка каждого теста:

```java
testDataPreparer.prepare(TestDataScenario.XXX);
```

Сами SQL-запросы хранятся в enum `TestDataScenario`.

## Ограничения текущего архива

В архив не включён реальный `wishlist.apk`, потому что он должен поставляться отдельно и раздаваться WireMock-сервером. Перед первым запуском добавьте APK в `wiremock/__files/`.
