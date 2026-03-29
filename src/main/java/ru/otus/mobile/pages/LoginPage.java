package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.config.TestUsersConfig.TestUser;

@Singleton
public class LoginPage extends AbsBasePage {

  public void login(TestUser user) {
    $(AppiumBy.id("ru.otus.wishlist:id/username_text_input")).shouldBe(visible).click();
    $(AppiumBy.id("ru.otus.wishlist:id/username_text_input")).setValue(user.username());

    $(AppiumBy.id("ru.otus.wishlist:id/password_text_input")).shouldBe(visible).click();
    $(AppiumBy.id("ru.otus.wishlist:id/password_text_input")).setValue(user.password());

    $(AppiumBy.id("ru.otus.wishlist:id/log_in_button")).shouldBe(visible).click();
  }
}
