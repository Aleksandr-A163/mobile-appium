package ru.otus.mobile.config;

import java.util.Properties;
import javax.inject.Singleton;

@Singleton
public class TestUsersConfig {

  private final Properties properties = PropertiesLoader.load("test-users.properties");

  public TestUser wishlistCreateUser() {
    return user("wishlist.create");
  }

  public TestUser wishlistEditUser() {
    return user("wishlist.edit");
  }

  public TestUser giftUser() {
    return user("gift");
  }

  public TestUser reservationUser() {
    return user("reservation");
  }

  private TestUser user(String prefix) {
    return new TestUser(
        properties.getProperty(prefix + ".username"), properties.getProperty(prefix + ".password"));
  }

  public record TestUser(String username, String password) {}
}
