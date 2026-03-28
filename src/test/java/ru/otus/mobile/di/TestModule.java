package ru.otus.mobile.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.otus.mobile.config.TestConfig;
import ru.otus.mobile.driver.EmulatorPool;
import ru.otus.mobile.driver.MobileDriverFactory;

public class TestModule extends AbstractModule {

  @Override
  protected void configure() {}

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
