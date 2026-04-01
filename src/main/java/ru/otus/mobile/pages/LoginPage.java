package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.config.TestUsersConfig.TestUser;

@Singleton
public class LoginPage extends AbsBasePage {

  private final SelenideElement usernameInputField =
      $(AppiumBy.id("ru.otus.wishlist:id/username_text_input"));
  private final SelenideElement passwordInputField =
      $(AppiumBy.id("ru.otus.wishlist:id/password_text_input"));
  private final SelenideElement loginButton = $(AppiumBy.id("ru.otus.wishlist:id/log_in_button"));

  public LoginPage checkOpened() {
    usernameInputField.shouldBe(visible);
    passwordInputField.shouldBe(visible);
    loginButton.shouldBe(visible);
    return this;
  }

  public void login(TestUser user) {
    checkOpened();
    usernameInputField.setValue(user.username());
    passwordInputField.setValue(user.password());
    loginButton.click();
  }
}
