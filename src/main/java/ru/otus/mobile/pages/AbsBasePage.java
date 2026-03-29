package ru.otus.mobile.pages;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import ru.otus.mobile.components.BottomNavComponent;

public abstract class AbsBasePage extends AbsPageObject {

  protected final BottomNavComponent bottomNav = new BottomNavComponent();

  public void shouldShowToolbar() {
    $(AppiumBy.id("ru.otus.wishlist:id/toolbar")).shouldBe(Condition.visible);
  }

  public void shouldShowBottomNavigation() {
    bottomNav.shouldBeVisible();
  }
}
