package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.appium.SelenideAppium.$$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;

@Singleton
public class WishlistPage extends AbsBasePage {

  public void openCreateWishlistForm() {
    $(AppiumBy.id("ru.otus.wishlist:id/add_button")).should(exist).click();
  }

  public void createWishlist(String title) {
    $(AppiumBy.id("ru.otus.wishlist:id/title_input")).should(exist).setValue(title);
    $(AppiumBy.id("ru.otus.wishlist:id/description_input"))
        .should(exist)
        .setValue("Created by autotest");
    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
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
        .should(exist)
        .click();
  }

  public void editAnyWishlistExcept(String forbiddenTitle, String newTitle) {
    $(AppiumBy.id("ru.otus.wishlist:id/wishlists")).should(exist);

    int count = $$(AppiumBy.id("ru.otus.wishlist:id/wishlist_item")).size();

    for (int i = 0; i < count; i++) {
      SelenideElement item = $$(AppiumBy.id("ru.otus.wishlist:id/wishlist_item")).get(i);
      SelenideElement titleElement = item.$(AppiumBy.id("ru.otus.wishlist:id/title"));
      String actualTitle = titleElement.should(exist).getText();

      if (actualTitle != null && !actualTitle.trim().equals(forbiddenTitle)) {
        item.$(AppiumBy.id("ru.otus.wishlist:id/edit_button")).should(exist).click();

        SelenideElement titleInput = $(AppiumBy.id("ru.otus.wishlist:id/title_input"));
        titleInput.should(exist).clear();
        titleInput.setValue(newTitle);

        SelenideElement descriptionInput = $(AppiumBy.id("ru.otus.wishlist:id/description_input"));
        descriptionInput.should(exist).clear();
        descriptionInput.setValue("Updated by autotest");

        $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
        return;
      }
    }

    throw new AssertionError("Не найден ни один список желаний, отличный от: " + forbiddenTitle);
  }

  public void editOpenedWishlist(String newTitle) {
    $(AppiumBy.id("ru.otus.wishlist:id/edit_button")).should(exist).click();

    SelenideElement titleInput = $(AppiumBy.id("ru.otus.wishlist:id/title_input"));
    titleInput.should(exist).clear();
    titleInput.setValue(newTitle);

    SelenideElement descriptionInput = $(AppiumBy.id("ru.otus.wishlist:id/description_input"));
    descriptionInput.should(exist).clear();
    descriptionInput.setValue("Updated by autotest");

    $(AppiumBy.id("ru.otus.wishlist:id/save_button")).should(exist).click();
  }

  public void openAnyWishlist() {
    $(AppiumBy.id("ru.otus.wishlist:id/mine_menu")).should(exist).click();
    $(AppiumBy.id("ru.otus.wishlist:id/wishlists")).should(exist);
    $$(AppiumBy.id("ru.otus.wishlist:id/wishlist_item")).first().should(exist).click();
  }
}
