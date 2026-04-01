package ru.otus.mobile.extensions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import ru.otus.mobile.di.TestModule;
import ru.otus.mobile.driver.EmulatorPool;
import ru.otus.mobile.driver.MobileDriverFactory;
import ru.otus.mobile.driver.MobileSession;
import ru.otus.mobile.driver.MobileSessionContext;

public class DriverExtension implements BeforeEachCallback, AfterEachCallback {

  private static Injector injector;

  private static synchronized Injector getOrCreateInjector() {
    if (injector == null) {
      injector = Guice.createInjector(new TestModule());
    }
    return injector;
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    Injector injector = getOrCreateInjector();
    injector.injectMembers(context.getRequiredTestInstance());

    EmulatorPool emulatorPool = injector.getInstance(EmulatorPool.class);
    MobileDriverFactory driverFactory = injector.getInstance(MobileDriverFactory.class);

    MobileSession session = emulatorPool.take();
    MobileSessionContext.set(session);

    WebDriver driver = driverFactory.create(session);
    WebDriverRunner.setWebDriver(driver);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    try {
      if (WebDriverRunner.hasWebDriverStarted()) {
        Selenide.closeWebDriver();
      }
    } finally {
      Injector injector = getOrCreateInjector();
      EmulatorPool emulatorPool = injector.getInstance(EmulatorPool.class);
      MobileSession session = MobileSessionContext.get();
      if (session != null) {
        emulatorPool.release(session);
      }
      MobileSessionContext.clear();
    }
  }
}
