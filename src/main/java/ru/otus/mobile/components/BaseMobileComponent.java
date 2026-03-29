package ru.otus.mobile.components;

import com.codeborne.selenide.SelenideElement;
import ru.otus.mobile.pages.AbsPageObject;

public abstract class BaseMobileComponent extends AbsPageObject {

  protected final SelenideElement root;

  protected BaseMobileComponent(SelenideElement root) {
    this.root = root;
  }
}
