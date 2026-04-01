package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.GiftListContent;

@Singleton
public class WishlistDetailsPage extends AbsBasePage {

  private final SelenideElement giftsRoot = $(AppiumBy.id("ru.otus.wishlist:id/gifts"));

  public WishlistDetailsPage checkOpened() {
    giftsRoot.shouldBe(visible);
    return this;
  }

  public GiftListContent gifts() {
    return new GiftListContent(giftsRoot);
  }

  public WishlistDetailsPage reserveGift(int index) {
    gifts().get(index).switchReserved();
    return this;
  }

  public WishlistDetailsPage reserveGift(String title) {
    gifts().findByTitle(title).switchReserved();
    return this;
  }

  public WishlistDetailsPage shouldShowReservedStatus(int index) {
    gifts().get(index).assertReserved();
    return this;
  }

  public WishlistDetailsPage shouldShowReservedStatus(String title) {
    gifts().findByTitle(title).assertReserved();
    return this;
  }
}
