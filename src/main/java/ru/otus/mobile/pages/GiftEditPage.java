package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import java.util.concurrent.ThreadLocalRandom;
import ru.otus.mobile.components.GiftCardComponent;
import ru.otus.mobile.components.GiftListContent;

@Singleton
public class GiftEditPage extends AbsBasePage {

  private final SelenideElement giftsRoot = $(AppiumBy.id("ru.otus.wishlist:id/gifts_content"));
  private final SelenideElement noResultsBlock =
      $(AppiumBy.id("ru.otus.wishlist:id/gifts_no_results"));
  private final SelenideElement noResultsText =
      $(AppiumBy.id("ru.otus.wishlist:id/no_results_text"));

  private final SelenideElement addButton = $(AppiumBy.id("ru.otus.wishlist:id/add_button"));
  private final SelenideElement saveButton = $(AppiumBy.id("ru.otus.wishlist:id/save_button"));
  private final SelenideElement nameInput = $(AppiumBy.id("ru.otus.wishlist:id/name_input"));
  private final SelenideElement priceInput = $(AppiumBy.id("ru.otus.wishlist:id/price_input"));
  private final SelenideElement descriptionInput =
      $(AppiumBy.id("ru.otus.wishlist:id/description_input"));
  private final SelenideElement giftEditTitle =
      $(AppiumBy.id("ru.otus.wishlist:id/gift_edit_title"));

  public GiftEditPage checkOpened() {
    topAppBar.shouldBeVisible();
    bottomNav.shouldBeVisible();
    addButton.shouldBe(visible);
    return this;
  }

  public GiftListContent gifts() {
    return new GiftListContent(giftsRoot);
  }

  public void shouldShowEmptyState() {
    noResultsBlock.shouldBe(visible);
    noResultsText.shouldBe(visible);
  }

  public void openCreateGiftForm() {
    checkOpened();
    addButton.should(exist).click();
    saveButton.should(exist);
  }

  public void createGift(String title) {
    createGift(title, 2500, "Created by autotest");
  }

  public void createGift(String title, int price, String description) {
    nameInput.should(exist).setValue(title);
    priceInput.should(exist).setValue(String.valueOf(price));
    descriptionInput.should(exist).setValue(description);
    saveButton.should(exist).click();
  }

  public int generateRandomPrice(int minInclusive, int maxInclusive) {
    return ThreadLocalRandom.current().nextInt(minInclusive, maxInclusive + 1);
  }

  public void editFirstGiftPrice(int newPrice) {
    GiftCardComponent firstGift = gifts().first();
    firstGift.clickEdit();

    giftEditTitle.should(exist);
    priceInput.should(exist).clear();
    priceInput.setValue(String.valueOf(newPrice));
    saveButton.should(exist).click();
  }

  public void editGiftByTitle(String currentTitle, String newTitle) {
    GiftCardComponent giftItem = gifts().findByTitle(currentTitle);
    giftItem.clickEdit();

    giftEditTitle.should(exist);
    nameInput.should(exist).clear();
    nameInput.setValue(newTitle);
    saveButton.should(exist).click();
  }

  public void shouldContainPrice(int price) {
    String expectedPrice = price + " ₽";
    int count = gifts().size();

    for (int i = 1; i <= count; i++) {
      try {
        gifts().get(i).assertPriceEqualsTo(expectedPrice);
        return;
      } catch (AssertionError ignored) {
      }
    }

    throw new AssertionError("Не найдена цена подарка: " + expectedPrice);
  }

  public void shouldContainGift(String title) {
    gifts().findByTitle(title);
  }
}
