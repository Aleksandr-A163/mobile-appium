package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;

@Singleton
public class WishlistPage extends AbsBasePage {

  public void openCreateWishlistForm() {
    $(AppiumBy.id("ru.otus.wishlist:id/add_button")).should(exist).click();
  }

  public void createWishlist(String title) {
    $(AppiumBy.id("ru.otus.wishlist:id/title_input")).setValue(title);
    $(AppiumBy.id("ru.otus.wishlist:id/description_input")).setValue("Created by autotest");
    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).click();
  }

  public void shouldContainWishlist(String title) {
    $(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().resourceId(\"ru.otus.wishlist:id/wishlists\"))"
                + ".scrollIntoView(new UiSelector().text(\""
                + title
                + "\"))"))
        .should(exist);
  }

  public void openWishlist(String title) {
    $(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().resourceId(\"ru.otus.wishlist:id/wishlists\"))"
                + ".scrollIntoView(new UiSelector().text(\""
                + title
                + "\"))"))
        .click();
  }

  public void editWishlist(String newTitle) {
    $(AppiumBy.id("ru.otus.wishlist:id/title_input")).should(exist).clear();
    $(AppiumBy.id("ru.otus.wishlist:id/title_input")).setValue(newTitle);

    $(AppiumBy.id("ru.otus.wishlist:id/description_input")).should(exist).clear();
    $(AppiumBy.id("ru.otus.wishlist:id/description_input")).setValue("Updated by autotest");

    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
  }
}
