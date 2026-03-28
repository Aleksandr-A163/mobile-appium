package ru.otus.mobile.pages;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import javax.inject.Singleton;
import ru.otus.mobile.config.TestUsersConfig.TestUser;

@Singleton
public class LoginPage extends AbsBasePage {

  public void login(TestUser user) {
    $(AppiumBy.id("ru.otus.wishlist:id/login"))
        .shouldBe(Condition.visible)
        .setValue(user.username());
    $(AppiumBy.id("ru.otus.wishlist:id/password")).setValue(user.password());
    $(AppiumBy.id("ru.otus.wishlist:id/sign_in_button")).click();
  }
}
