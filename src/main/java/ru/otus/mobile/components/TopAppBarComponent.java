package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class TopAppBarComponent extends BaseMobileComponent<TopAppBarComponent> {

  private final SelenideElement title = root.$(AppiumBy.id("ru.otus.wishlist:id/toolbar_title"));
  private final SelenideElement filterButton = root.$(AppiumBy.id("ru.otus.wishlist:id/filter"));
  private final SelenideElement addButton = root.$(AppiumBy.id("ru.otus.wishlist:id/add_button"));

  public TopAppBarComponent() {
    super($(AppiumBy.id("ru.otus.wishlist:id/toolbar")));
  }

  public TopAppBarComponent shouldBeVisible() {
    root.shouldBe(visible);
    return this;
  }

  public TopAppBarComponent shouldHaveTitle(String expectedTitle) {
    title.shouldBe(visible).shouldHave(text(expectedTitle));
    return this;
  }

  public void clickFilter() {
    filterButton.shouldBe(visible).click();
  }

  public void clickAdd() {
    addButton.shouldBe(visible).click();
  }
}