package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.UserWishlistsContent;

@Singleton
public class UserWishlistsPage extends AbsBasePage {

  private final SelenideElement wishlistsRoot =
      $(AppiumBy.id("ru.otus.wishlist:id/wishlists_content"));

  public UserWishlistsPage checkOpened() {
    topAppBar.shouldBeVisible();
    bottomNav.shouldBeVisible();
    wishlistsRoot.shouldBe(visible);
    return this;
  }

  private UserWishlistsContent wishlists() {
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
