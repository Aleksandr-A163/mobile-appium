package ru.otus.mobile.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import io.appium.java_client.AppiumBy;

@Singleton
public class FilterUserPage extends AbsBasePage {

  @Inject private Provider<UsersPage> usersPageProvider;

  private final SelenideElement filterRoot =
      $(AppiumBy.id("ru.otus.wishlist:id/users_filter_bottom_sheet"));
  private final SelenideElement usernameInput =
      $(AppiumBy.id("ru.otus.wishlist:id/username_input"));
  private final SelenideElement applyButton = $(AppiumBy.id("ru.otus.wishlist:id/apply_button"));

  public FilterUserPage checkOpened() {
    filterRoot.shouldBe(visible);
    usernameInput.shouldBe(visible);
    applyButton.shouldBe(visible);
    return this;
  }

  public FilterUserPage setUsername(String username) {
    usernameInput.clear();
    usernameInput.setValue(username);
    return this;
  }

  public UsersPage apply() {
    applyButton.shouldBe(visible).click();
    return usersPageProvider.get().checkOpened();
  }
}
