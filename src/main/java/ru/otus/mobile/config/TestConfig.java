package ru.otus.mobile.config;

import com.google.inject.Singleton;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Singleton
public class TestConfig {

  private final Properties properties;

  public TestConfig() {
    this.properties = PropertiesLoader.load("application.properties");
  }

  public String appiumUrl() {
    return System.getProperty("appium.url", properties.getProperty("appium.url"));
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

  public List<String> deviceNames() {
    return split(
        System.getProperty(
            "device.names", properties.getProperty("device.names", "emulator-5554")));
  }

  public List<String> serverUrls() {
    return split(
        System.getProperty("server.urls", properties.getProperty("server.urls", appiumUrl())));
  }

  private List<String> split(String value) {
    return Arrays.stream(value.split(","))
        .map(String::trim)
        .filter(item -> !item.isBlank())
        .collect(Collectors.toList());
  }
}
