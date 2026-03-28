package ru.otus.mobile.driver;

import com.codeborne.selenide.WebDriverRunner;
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
            .setApp(config.appDownloadUrl())
            .setAppPackage(config.appPackage())
            .setAppActivity(config.appActivity())
            .autoGrantPermissions();

    try {
      AndroidDriver driver = new AndroidDriver(new URL(session.appiumServerUrl()), options);
      WebDriverRunner.setWebDriver(driver);
      return driver;
    } catch (MalformedURLException exception) {
      throw new IllegalStateException(
          "Invalid Appium URL: " + session.appiumServerUrl(), exception);
    }
  }
}
