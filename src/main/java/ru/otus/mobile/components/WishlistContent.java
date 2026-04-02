package ru.otus.mobile.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class WishlistContent extends BaseMobileComponent<WishlistContent> {

  private final ElementsCollection items =
      root.$$(AppiumBy.id("ru.otus.wishlist:id/wishlist_item"));

  public WishlistContent(SelenideElement root) {
    super(root);
  }

  public WishlistCardComponent get(int index) {
    return new WishlistCardComponent(items.get(index - 1));
  }

  public WishlistCardComponent first() {
    return new WishlistCardComponent(items.first());
  }

  public WishlistCardComponent findByTitle(String title) {
    for (SelenideElement item : items) {
      WishlistCardComponent component = new WishlistCardComponent(item);
      try {
        component.assertTitleEqualsTo(title);
        return component;
      } catch (AssertionError ignored) {
      }
    }
    throw new AssertionError("Wishlist was not found by title: " + title);
  }

  public WishlistContent shouldContainTitle(String title) {
    findByTitle(title);
    return this;
  }
}
