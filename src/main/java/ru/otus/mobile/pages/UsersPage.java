package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.UsersMenuContent;

@Singleton
public class UsersPage extends AbsBasePage {

  @Inject private Provider<FilterUserPage> filterUserPageProvider;
  @Inject private Provider<UserWishlistsPage> userWishlistsPageProvider;

  private final SelenideElement usersRoot = $(AppiumBy.id("ru.otus.wishlist:id/users"));
  private final SelenideElement filterButton = $(AppiumBy.id("ru.otus.wishlist:id/filter"));

  public UsersPage checkOpened() {
    usersRoot.shouldBe(visible);
    return this;
  }

  public FilterUserPage clickFilter() {
    filterButton.shouldBe(visible).click();
    return filterUserPageProvider.get().checkOpened();
  }

  public UsersMenuContent users() {
    return new UsersMenuContent(usersRoot);
  }

  public UserWishlistsPage openUser(int index) {
    users().get(index).clickItem();
    return userWishlistsPageProvider.get().checkOpened();
  }

  public UserWishlistsPage openUser(String username) {
    users().findByName(username).clickItem();
    return userWishlistsPageProvider.get().checkOpened();
  }
}
