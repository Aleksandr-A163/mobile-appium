package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.WishlistCardComponent;
import ru.otus.mobile.components.WishlistContent;

@Singleton
public class WishlistPage extends AbsBasePage {

  private final SelenideElement addButton = $(AppiumBy.id("ru.otus.wishlist:id/add_button"));
  private final SelenideElement wishlistsRoot = $(AppiumBy.id("ru.otus.wishlist:id/wishlists"));
  private final SelenideElement titleInput = $(AppiumBy.id("ru.otus.wishlist:id/title_input"));
  private final SelenideElement descriptionInput =
      $(AppiumBy.id("ru.otus.wishlist:id/description_input"));
  private final SelenideElement saveButton = $(AppiumBy.id("ru.otus.wishlist:id/save_button"));

  public WishlistPage checkOpened() {
    wishlistsRoot.shouldBe(visible);
    return this;
  }

  public WishlistContent wishlistContent() {
    return new WishlistContent(wishlistsRoot);
  }

  public void openCreateWishlistForm() {
    addButton.should(exist).click();
    titleInput.should(exist);
    descriptionInput.should(exist);
    saveButton.should(exist);
  }

  public void createWishlist(String title) {
    titleInput.should(exist).setValue(title);
    descriptionInput.should(exist).setValue("Created by autotest");
    saveButton.should(exist).click();
  }

  public void shouldContainWishlist(String title) {
    wishlistContent().shouldContainTitle(title);
  }

  public void editAnyWishlistExcept(String forbiddenTitle, String newTitle) {
    checkOpened();

    int count = wishlistContent().size();

    for (int i = 1; i <= count; i++) {
      WishlistCardComponent item = wishlistContent().get(i);
      String actualTitle = item.titleText();

      if (actualTitle != null && !actualTitle.trim().equals(forbiddenTitle)) {
        item.clickEdit();
        titleInput.should(exist).clear();
        titleInput.setValue(newTitle);

        descriptionInput.should(exist).clear();
        descriptionInput.setValue("Updated by autotest");

        saveButton.should(exist).click();
        return;
      }
    }

    throw new AssertionError("Не найден ни один список желаний, отличный от: " + forbiddenTitle);
  }

  public void openAnyWishlist() {
    bottomNav.openMine();
    checkOpened();
    wishlistContent().first().clickItem();
  }

  public void openWishlist(String title) {
    bottomNav.openMine();
    checkOpened();
    wishlistContent().findByTitle(title).clickItem();
  }

  public void openWishlistForEdit(String title) {
    bottomNav.openMine();
    checkOpened();
    wishlistContent().findByTitle(title).clickEdit();
  }

  public void openUsers() {
    bottomNav.openUsers();
  }
}