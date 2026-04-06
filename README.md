# Mobile Appium Tests

Проект с UI-автотестами для Android-приложения **Wishlist** (`ru.otus.wishlist`) на базе **Java + JUnit 5 + Selenide + Appium + Guice**.

## Что есть в проекте

* UI-тесты для основных пользовательских сценариев
* Page Object + component-based подход
* Dependency Injection через Guice
* Единый `Injector` на весь проект
* Singleton-страницы и singleton-конфигурация через Guice
* Переходы между страницами через `Provider<T>`, без ручного `new`
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
* Docker Compose

## Структура проекта

* `src/main/java/ru/otus/mobile/config` — конфигурация проекта и тестовых данных
* `src/main/java/ru/otus/mobile/data` — подготовка тестовых данных в БД
* `src/main/java/ru/otus/mobile/di` — Guice module и wiring зависимостей
* `src/main/java/ru/otus/mobile/driver` — драйвер, мобильные сессии, пул эмуляторов
* `src/main/java/ru/otus/mobile/pages` — Page Object
* `src/main/java/ru/otus/mobile/components` — переиспользуемые UI-компоненты
* `src/main/java/ru/otus/mobile/extensions` — JUnit 5 extensions
* `src/test/java/ru/otus/mobile/tests` — тесты

## Покрытые сценарии

* создание wishlist
* редактирование wishlist
* создание gift
* редактирование gift
* редактирование цены gift
* изменение статуса резерва gift у другого пользователя

## Архитектурные особенности

* `DriverExtension` создает единый Guice `Injector` и управляет жизненным циклом драйвера
* `MobileDriverFactory` отвечает только за создание `AndroidDriver`
* `WebDriverRunner.setWebDriver(...)` вызывается в extension, а не в factory
* Page Object-классы используются как singleton-компоненты через Guice
* переходы между страницами выполняются через `Provider<T>`, поэтому объекты не создаются вручную
* поиск карточек и элементов выполняется через сравнение значений (`getTitle()`, `getName()`), без `assert` внутри бизнес-логики поиска
* подготовка тестовых данных приводится к предсказуемому состоянию под каждый сценарий
* SQL-запросы выполняются безопасно через `PreparedStatement`
* персональные данные из БД не выводятся в консоль

## Запуск тестов

### Все тесты

```bash
./gradlew test "-DdatabaseUsername=student" "-DdatabasePassword=student"
