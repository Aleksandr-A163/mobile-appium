package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.GiftListContent;

@Singleton
public class WishlistDetailsPage extends AbsBasePage {

  private final SelenideElement giftsRoot = $(AppiumBy.id("ru.otus.wishlist:id/gifts_content"));
  private final SelenideElement noResultsBlock =
      $(AppiumBy.id("ru.otus.wishlist:id/gifts_no_results"));
  private final SelenideElement noResultsText =
      $(AppiumBy.id("ru.otus.wishlist:id/no_results_text"));

  public WishlistDetailsPage checkOpened() {
    topAppBar.shouldBeVisible();
    bottomNav.shouldBeVisible();
    return this;
  }

  private GiftListContent gifts() {
    return new GiftListContent(giftsRoot);
  }

  public WishlistDetailsPage shouldShowEmptyState() {
    noResultsBlock.shouldBe(visible);
    noResultsText.shouldBe(visible);
    return this;
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
