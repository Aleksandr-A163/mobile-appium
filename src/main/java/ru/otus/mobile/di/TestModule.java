package ru.otus.mobile.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.otus.mobile.config.TestConfig;
import ru.otus.mobile.config.TestUsersConfig;
import ru.otus.mobile.data.TestDataPreparer;
import ru.otus.mobile.driver.EmulatorPool;
import ru.otus.mobile.driver.MobileDriverFactory;
import ru.otus.mobile.pages.GiftEditPage;
import ru.otus.mobile.pages.LoginPage;
import ru.otus.mobile.pages.WishlistPage;

public class TestModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(TestUsersConfig.class).in(Singleton.class);
    bind(TestDataPreparer.class).in(Singleton.class);

    bind(LoginPage.class);
    bind(WishlistPage.class);
    bind(GiftEditPage.class);
  }

  @Provides
  @Singleton
  public TestConfig provideTestConfig() {
    return new TestConfig();
  }

  @Provides
  @Singleton
  public EmulatorPool provideEmulatorPool(TestConfig config) {
    return new EmulatorPool(config);
  }

  @Provides
  @Singleton
  public MobileDriverFactory provideMobileDriverFactory(TestConfig config) {
    return new MobileDriverFactory(config);
  }
}
