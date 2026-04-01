package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.UserWishlistsContent;

@Singleton
public class UserWishlistsPage extends AbsBasePage {

  private final SelenideElement wishlistsRoot = $(AppiumBy.id("ru.otus.wishlist:id/wishlists"));

  public UserWishlistsPage checkOpened() {
    wishlistsRoot.shouldBe(visible);
    return this;
  }

  public UserWishlistsContent wishlists() {
    return new UserWishlistsContent(wishlistsRoot);
  }

  public WishlistDetailsPage openWishlist(int index) {
    wishlists().get(index).clickItem();
    return new WishlistDetailsPage().checkOpened();
  }

  public WishlistDetailsPage openWishlist(String title) {
    wishlists().findByTitle(title).clickItem();
    return new WishlistDetailsPage().checkOpened();
  }
}