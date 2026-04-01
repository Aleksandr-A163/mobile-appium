package ru.otus.mobile.components;

import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import ru.otus.mobile.pages.AbsPageObject;

public abstract class BaseMobileComponent<T extends BaseMobileComponent<T>> extends AbsPageObject {

  protected final SelenideElement root;

  protected BaseMobileComponent(SelenideElement root) {
    this.root = root;
  }

  @SuppressWarnings("unchecked")
  public T shouldBe(WebElementCondition condition) {
    root.shouldBe(condition);
    return (T) this;
  }

  public void clickItem() {
    root.shouldBe(visible).click();
  }
}
