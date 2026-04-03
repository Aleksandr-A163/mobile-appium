package ru.otus.mobile.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class UsersMenuContent extends BaseMobileComponent<UsersMenuContent> {

  private final ElementsCollection items = root.$$(AppiumBy.id("ru.otus.wishlist:id/user_item"));

  public UsersMenuContent(SelenideElement root) {
    super(root);
  }

  public UsersMenuItem get(int index) {
    return new UsersMenuItem(items.get(index - 1));
  }

  public UsersMenuItem findByName(String value) {
    for (SelenideElement item : items) {
      UsersMenuItem user = new UsersMenuItem(item);
      if (value.equals(user.getName())) {
        return user;
      }
    }
    throw new AssertionError("User was not found in users list: " + value);
  }
}
