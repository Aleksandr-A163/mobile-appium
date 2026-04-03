package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class UsersMenuItem extends BaseMobileComponent<UsersMenuItem> {

  private final SelenideElement name = root.$(AppiumBy.id("ru.otus.wishlist:id/username"));

  public UsersMenuItem(SelenideElement root) {
    super(root);
  }

  public String getName() {
    return name.shouldBe(visible).getText();
  }
}
