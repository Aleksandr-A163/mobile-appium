package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.text;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class UserWishlistItemComponent extends BaseMobileComponent<UserWishlistItemComponent> {

  private final SelenideElement title = root.$(AppiumBy.id("ru.otus.wishlist:id/title"));

  public UserWishlistItemComponent(SelenideElement root) {
    super(root);
  }

  public UserWishlistItemComponent assertTitleEqualsTo(String value) {
    title.shouldHave(text(value));
    return this;
  }
}
