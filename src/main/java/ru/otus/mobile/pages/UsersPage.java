package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.UsersMenuContent;

public class UsersPage extends AbsBasePage {

  private final SelenideElement usersRoot = $(AppiumBy.id("ru.otus.wishlist:id/users"));
  private final SelenideElement filterButton = $(AppiumBy.id("ru.otus.wishlist:id/filter"));

  public UsersPage checkOpened() {
    usersRoot.shouldBe(visible);
    return this;
  }

  public FilterUserPage clickFilter() {
    filterButton.shouldBe(visible).click();
    return new FilterUserPage().checkOpened();
  }

  public UsersMenuContent users() {
    return new UsersMenuContent(usersRoot);
  }

  public UserWishlistsPage openUser(int index) {
    users().get(index).clickItem();
    return new UserWishlistsPage().checkOpened();
  }

  public UserWishlistsPage openUser(String username) {
    users().findByName(username).clickItem();
    return new UserWishlistsPage().checkOpened();
  }
}
