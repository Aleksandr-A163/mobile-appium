package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class WishlistCardComponent extends BaseMobileComponent<WishlistCardComponent> {

  private final SelenideElement title = root.$(AppiumBy.id("ru.otus.wishlist:id/title"));
  private final SelenideElement subtitle = root.$(AppiumBy.id("ru.otus.wishlist:id/subtitle"));
  private final SelenideElement editButton = root.$(AppiumBy.id("ru.otus.wishlist:id/edit_button"));

  public WishlistCardComponent(SelenideElement root) {
    super(root);
  }

  public WishlistCardComponent assertTitleEqualsTo(String value) {
    title.shouldHave(exactText(value));
    return this;
  }

  public WishlistCardComponent assertSubtitleEqualsTo(String value) {
    subtitle.shouldHave(exactText(value));
    return this;
  }

  public String titleText() {
    return title.shouldBe(visible).getText();
  }

  public void clickEdit() {
    editButton.shouldBe(visible).click();
  }
}
