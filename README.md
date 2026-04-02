# Mobile Appium Tests

Проект с UI-автотестами для Android-приложения **Wishlist** (`ru.otus.wishlist`) на базе **Java + JUnit 5 + Selenide + Appium + Guice**.

## Что есть в проекте

* UI-тесты для основных пользовательских сценариев
* Page Object + component-based подход
* Dependency Injection через Guice
* Единый `Injector` на весь проект
* Пул мобильных сессий через `EmulatorPool`
* Подготовка тестовых данных через JDBC и `PreparedStatement`
* Конфигурация через `.properties` и system properties

## Технологии

* Java 17
* Gradle
* JUnit 5
* Selenide
* Appium
* Selenium WebDriver
* Guice
* PostgreSQL

## Структура проекта

* `src/main/java/ru/otus/mobile/config` — конфигурация проекта и тестовых данных
* `src/main/java/ru/otus/mobile/data` — подготовка тестовых данных в БД
* `src/main/java/ru/otus/mobile/driver` — драйвер, мобильные сессии, пул эмуляторов
* `src/main/java/ru/otus/mobile/pages` — Page Object
* `src/main/java/ru/otus/mobile/components` — переиспользуемые UI-компоненты
* `src/main/java/ru/otus/mobile/extensions` — JUnit 5 extensions
* `src/test/java/ru/otus/mobile/tests` — тесты

## Покрытые сценарии

* создание wishlist
* редактирование wishlist
* создание gift
* редактирование цены gift
* изменение статуса резерва gift у другого пользователя

## Запуск тестов

### Все тесты

```bash
./gradlew clean test
```

### Отдельный тестовый класс

```bash
./gradlew test --tests "ru.otus.mobile.tests.GiftTest"
```

### Отдельный тестовый метод

```bash
./gradlew test --tests "ru.otus.mobile.tests.GiftTest.shouldFixGiftPrice" "-DdatabaseUsername=student" "-DdatabasePassword=student"
```

## Настройки

Основные настройки читаются из property-файлов и могут быть переопределены через `-D...`:

* `appium.host`
* `app.download.url`
* `app.package`
* `app.activity`
* `platform.name`
* `automation.name`
* `databaseUrl`
* `databaseUsername`
* `databasePassword`
* `testdata.wishlist.base.title`
* `testdata.wishlist.base.description`
* `testdata.gift.base.name`
* `testdata.gift.base.description`
* `testdata.gift.base.price`

Пользователи для тестов задаются в `test-users.properties`.

## Особенности реализации

* `DriverExtension` управляет жизненным циклом драйвера и инъекцией зависимостей
* `MobileDriverFactory` отвечает только за создание драйвера
* `WebDriverRunner.setWebDriver(...)` вызывается в extension, а не в factory
* Подготовка тестовых данных упрощена до предсказуемого состояния под каждый сценарий
* SQL-запросы выполняются безопасно через `PreparedStatement`
* Секреты БД не выводятся в консоль

## Требования к окружению

Для запуска нужны:

* доступный Appium server
* Android emulator / device
* доступ к тестовой PostgreSQL БД
* корректные значения в properties или `-D` параметрах
