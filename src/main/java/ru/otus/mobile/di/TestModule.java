package ru.otus.mobile.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.otus.mobile.config.TestConfig;
import ru.otus.mobile.config.TestDataConfig;
import ru.otus.mobile.config.TestUsersConfig;
import ru.otus.mobile.driver.EmulatorPool;
import ru.otus.mobile.driver.MobileDriverFactory;

public class TestModule extends AbstractModule {

  @Provides
  @Singleton
  public TestConfig provideTestConfig() {
    return new TestConfig();
  }

  @Provides
  @Singleton
  public TestUsersConfig provideTestUsersConfig() {
    return new TestUsersConfig();
  }

  @Provides
  @Singleton
  public TestDataConfig provideTestDataConfig() {
    return new TestDataConfig();
  }

  @Provides
  @Singleton
  public EmulatorPool provideEmulatorPool() {
    return new EmulatorPool();
  }

  @Provides
  @Singleton
  public MobileDriverFactory provideMobileDriverFactory(TestConfig testConfig) {
    return new MobileDriverFactory(testConfig);
  }
}
