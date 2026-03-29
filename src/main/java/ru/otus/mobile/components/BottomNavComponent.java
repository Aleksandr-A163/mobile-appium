package ru.otus.mobile.components;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;

public class BottomNavComponent extends BaseMobileComponent {

  public BottomNavComponent() {
    super($(AppiumBy.id("ru.otus.wishlist:id/bottom_navigation")));
  }

  public void shouldBeVisible() {
    root.shouldBe(Condition.visible);
  }
}
