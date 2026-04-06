package ru.otus.mobile.driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import ru.otus.mobile.config.TestConfig;

@Singleton
public class MobileDriverFactory {

  private final TestConfig config;

  @Inject
  public MobileDriverFactory(TestConfig config) {
    this.config = config;
  }

  public WebDriver create(MobileSession session) {
    UiAutomator2Options options =
        new UiAutomator2Options()
            .setPlatformName(config.platformName())
            .setAutomationName(config.automationName())
            .setDeviceName(session.deviceName())
            .setAppPackage(config.appPackage())
            .setAppActivity(config.appActivity())
            .autoGrantPermissions();

    String appDownloadUrl = config.appDownloadUrl();
    if (appDownloadUrl != null && !appDownloadUrl.isBlank()) {
      options.setApp(appDownloadUrl);
    }

    String appiumUrl = session.appiumServerUrl(config.appiumHost());

    try {
      return new AndroidDriver(new URL(appiumUrl), options);
    } catch (MalformedURLException exception) {
      throw new IllegalStateException(
          "Invalid Appium URL: " + appiumUrl + " for session " + session.name(), exception);
    }
  }
}
