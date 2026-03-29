package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.appium.SelenideAppium.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import java.util.concurrent.ThreadLocalRandom;
import javax.inject.Singleton;

@Singleton
public class GiftEditPage extends AbsBasePage {

  public void openCreateGiftForm() {
    $(AppiumBy.id("ru.otus.wishlist:id/add_button")).should(exist).click();
    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist);
  }

  public void createGift(String title) {
    createGift(title, 2500, "Created by autotest");
  }

  public void createGift(String title, int price, String description) {
    $(AppiumBy.id("ru.otus.wishlist:id/name_input")).should(exist).setValue(title);
    $(AppiumBy.id("ru.otus.wishlist:id/price_input")).should(exist).setValue(String.valueOf(price));
    $(AppiumBy.id("ru.otus.wishlist:id/description_input")).should(exist).setValue(description);
    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
  }

  public int generateRandomPrice(int minInclusive, int maxInclusive) {
    return ThreadLocalRandom.current().nextInt(minInclusive, maxInclusive + 1);
  }

  public void editFirstGiftPrice(int newPrice) {
    SelenideElement firstGift =
        $$(AppiumBy.id("ru.otus.wishlist:id/gift_item")).first().should(exist);

    firstGift.$(AppiumBy.id("ru.otus.wishlist:id/edit_button")).should(exist).click();
    $(AppiumBy.id("ru.otus.wishlist:id/gift_edit_title")).should(exist);

    SelenideElement priceInput = $(AppiumBy.id("ru.otus.wishlist:id/price_input")).should(exist);
    priceInput.clear();
    priceInput.setValue(String.valueOf(newPrice));

    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
  }

  public void editGiftByTitle(String currentTitle, String newTitle) {
    SelenideElement giftItem = findGiftItemByTitle(currentTitle);

    giftItem.$(AppiumBy.id("ru.otus.wishlist:id/edit_button")).should(exist).click();
    $(AppiumBy.id("ru.otus.wishlist:id/gift_edit_title")).should(exist);

    SelenideElement nameInput = $(AppiumBy.id("ru.otus.wishlist:id/name_input")).should(exist);
    nameInput.clear();
    nameInput.setValue(newTitle);

    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
  }

  private SelenideElement findGiftItemByTitle(String title) {
    int count = $$(AppiumBy.id("ru.otus.wishlist:id/gift_item")).size();

    for (int i = 0; i < count; i++) {
      SelenideElement item = $$(AppiumBy.id("ru.otus.wishlist:id/gift_item")).get(i);
      String actualTitle = item.$(AppiumBy.id("ru.otus.wishlist:id/title")).should(exist).getText();

      if (title.equals(actualTitle)) {
        return item;
      }
    }

    throw new AssertionError("Не найден подарок с названием: " + title);
  }

  public void shouldContainPrice(int price) {
    String expectedPrice = price + " ₽";

    $$(AppiumBy.id("ru.otus.wishlist:id/price"))
        .shouldHave(CollectionCondition.itemWithText(expectedPrice));
  }

  public void shouldContainGift(String title) {
    $$(AppiumBy.id("ru.otus.wishlist:id/title"))
        .shouldHave(CollectionCondition.itemWithText(title));
  }
}
