package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class GiftCardComponent extends BaseMobileComponent<GiftCardComponent> {

  private final SelenideElement title = root.$(AppiumBy.id("ru.otus.wishlist:id/title"));
  private final SelenideElement subtitle = root.$(AppiumBy.id("ru.otus.wishlist:id/subtitle"));
  private final SelenideElement price = root.$(AppiumBy.id("ru.otus.wishlist:id/price"));
  private final SelenideElement editButton = root.$(AppiumBy.id("ru.otus.wishlist:id/edit_button"));
  private final SelenideElement reservedToggle =
      root.$(AppiumBy.id("ru.otus.wishlist:id/reserved"));

  public GiftCardComponent(SelenideElement root) {
    super(root);
  }

  public GiftCardComponent assertTitleEqualsTo(String value) {
    title.shouldHave(exactText(value));
    return this;
  }

  public String priceText() {
    return price.shouldBe(visible).getText();
  }

  public void clickEdit() {
    editButton.shouldBe(visible).click();
  }

  public void switchReserved() {
    reservedToggle.shouldBe(visible).click();
  }

  public void assertReserved() {
    reservedToggle.shouldHave(attribute("checked", "true"));
  }
}
