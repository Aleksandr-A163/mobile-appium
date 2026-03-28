package ru.otus.mobile.pages;

import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.appium.SelenideAppium.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import javax.inject.Singleton;

@Singleton
public class WishlistPage extends AbsBasePage {

  public void openCreateWishlistForm() {
    $(AppiumBy.id("ru.otus.wishlist:id/add_wishlist")).click();
  }

  public void createWishlist(String title) {
    $(AppiumBy.id("ru.otus.wishlist:id/title")).setValue(title);
    $(AppiumBy.id("ru.otus.wishlist:id/save")).click();
  }

  public void openWishlist(String title) {
    $$(AppiumBy.id("ru.otus.wishlist:id/title")).findBy(Condition.exactText(title)).click();
  }

  public void editWishlist(String newTitle) {
    $(AppiumBy.id("ru.otus.wishlist:id/edit")).click();
    $(AppiumBy.id("ru.otus.wishlist:id/title")).clear();
    $(AppiumBy.id("ru.otus.wishlist:id/title")).setValue(newTitle);
    $(AppiumBy.id("ru.otus.wishlist:id/save")).click();
  }

  public void shouldContainWishlist(String title) {
    $$(AppiumBy.id("ru.otus.wishlist:id/title"))
        .shouldHave(CollectionCondition.itemWithText(title));
  }
}
