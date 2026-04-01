package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class BottomNavComponent extends BaseMobileComponent<BottomNavComponent> {

  private final SelenideElement mineMenuButton =
      root.$(AppiumBy.id("ru.otus.wishlist:id/mine_menu"));
  private final SelenideElement usersMenuButton =
      root.$(AppiumBy.id("ru.otus.wishlist:id/users_menu"));

  public BottomNavComponent() {
    super($(AppiumBy.id("ru.otus.wishlist:id/bottom_navigation")));
  }

  public BottomNavComponent shouldBeVisible() {
    root.shouldBe(visible);
    return this;
  }

  public void openMine() {
    mineMenuButton.shouldBe(visible).click();
  }

  public void openUsers() {
    usersMenuButton.shouldBe(visible).click();
  }
}
