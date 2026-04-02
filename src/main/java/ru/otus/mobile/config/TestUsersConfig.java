package ru.otus.mobile.config;

import com.google.inject.Singleton;
import java.util.Properties;

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

  public TestUser giftEditUser() {
    return user("gift.edit");
  }

  public TestUser giftReservationUser() {
    return user("gift.reservation");
  }

  public TestUser giftReservationOwnerUser() {
    return user("gift.reservation.owner");
  }

  private TestUser user(String prefix) {
    String username =
        System.getProperty(prefix + ".username", properties.getProperty(prefix + ".username"));
    String password =
        System.getProperty(prefix + ".password", properties.getProperty(prefix + ".password"));

    return new TestUser(username, password);
  }

  public record TestUser(String username, String password) {}
}
