package ru.otus.mobile.pages;

import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.appium.SelenideAppium.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import javax.inject.Singleton;

@Singleton
public class GiftEditPage extends AbsBasePage {

  public void openCreateGiftForm() {
    $(AppiumBy.id("ru.otus.wishlist:id/add_gift")).click();
  }

  public void createGift(String title) {
    $(AppiumBy.id("ru.otus.wishlist:id/name")).setValue(title);
    $(AppiumBy.id("ru.otus.wishlist:id/save")).click();
  }

  public void openGift(String title) {
    $$(AppiumBy.id("ru.otus.wishlist:id/name")).findBy(Condition.exactText(title)).click();
  }

  public void editGift(String newTitle) {
    $(AppiumBy.id("ru.otus.wishlist:id/edit")).click();
    $(AppiumBy.id("ru.otus.wishlist:id/name")).clear();
    $(AppiumBy.id("ru.otus.wishlist:id/name")).setValue(newTitle);
    $(AppiumBy.id("ru.otus.wishlist:id/save")).click();
  }

  public void toggleReservation() {
    $(AppiumBy.id("ru.otus.wishlist:id/reserved")).click();
  }

  public void shouldContainGift(String title) {
    $$(AppiumBy.id("ru.otus.wishlist:id/name")).shouldHave(CollectionCondition.itemWithText(title));
  }
}
