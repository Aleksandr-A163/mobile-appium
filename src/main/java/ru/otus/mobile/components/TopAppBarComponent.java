package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class TopAppBarComponent extends BaseMobileComponent<TopAppBarComponent> {

  private final SelenideElement title = root.$(AppiumBy.className("android.widget.TextView"));

  public TopAppBarComponent() {
    super($(AppiumBy.id("ru.otus.wishlist:id/top_app_bar")));
  }

  public TopAppBarComponent shouldBeVisible() {
    root.shouldBe(visible);
    return this;
  }

  public TopAppBarComponent shouldHaveTitle(String expectedTitle) {
    title.shouldBe(visible).shouldHave(text(expectedTitle));
    return this;
  }
}
