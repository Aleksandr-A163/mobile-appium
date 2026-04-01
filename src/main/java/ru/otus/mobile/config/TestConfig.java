package ru.otus.mobile.config;

import com.google.inject.Singleton;
import java.time.Duration;
import java.util.Properties;

@Singleton
public class TestConfig {

  private final Properties properties;

  public TestConfig() {
    this.properties = PropertiesLoader.load("application.properties");
  }

  public String appiumHost() {
    return System.getProperty("appium.host", properties.getProperty("appium.host"));
  }

  public String appDownloadUrl() {
    return System.getProperty("app.download.url", properties.getProperty("app.download.url"));
  }

  public String appPackage() {
    return System.getProperty("app.package", properties.getProperty("app.package"));
  }

  public String appActivity() {
    return System.getProperty("app.activity", properties.getProperty("app.activity"));
  }

  public String platformName() {
    return System.getProperty("platform.name", properties.getProperty("platform.name"));
  }

  public String automationName() {
    return System.getProperty("automation.name", properties.getProperty("automation.name"));
  }

  public Duration timeout() {
    return Duration.ofMillis(
        Long.parseLong(
            System.getProperty("timeout.ms", properties.getProperty("timeout.ms", "10000"))));
  }
}
