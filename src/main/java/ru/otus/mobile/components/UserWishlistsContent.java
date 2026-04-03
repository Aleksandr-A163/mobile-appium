package ru.otus.mobile.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class UserWishlistsContent extends BaseMobileComponent<UserWishlistsContent> {

  private final ElementsCollection items =
      root.$$(AppiumBy.id("ru.otus.wishlist:id/wishlist_item"));

  public UserWishlistsContent(SelenideElement root) {
    super(root);
  }

  public UserWishlistItemComponent get(int index) {
    return new UserWishlistItemComponent(items.get(index - 1));
  }

  public UserWishlistItemComponent findByTitle(String value) {
    for (SelenideElement item : items) {
      UserWishlistItemComponent wishlist = new UserWishlistItemComponent(item);
      if (value.equals(wishlist.getTitle())) {
        return wishlist;
      }
    }
    throw new AssertionError("Wishlist was not found in user wishlists: " + value);
  }
}
