package ru.otus.mobile.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class GiftListContent extends BaseMobileComponent<GiftListContent> {

  private final ElementsCollection items = root.$$(AppiumBy.id("ru.otus.wishlist:id/gift_item"));

  public GiftListContent(SelenideElement root) {
    super(root);
  }

  public GiftCardComponent get(int index) {
    return new GiftCardComponent(items.get(index));
  }

  public GiftCardComponent first() {
    return new GiftCardComponent(items.first());
  }

  public GiftCardComponent findByTitle(String title) {
    for (SelenideElement item : items) {
      GiftCardComponent component = new GiftCardComponent(item);
      try {
        component.assertTitleEqualsTo(title);
        return component;
      } catch (AssertionError ignored) {
      }
    }
    throw new AssertionError("Gift was not found by title: " + title);
  }

  public int size() {
    return items.size();
  }
}
