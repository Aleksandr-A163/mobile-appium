package ru.otus.mobile.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ru.otus.mobile.config.TestUsersConfig;

@Singleton
public class TestDataPreparer {

  private static final String DEFAULT_DATABASE_URL =
      "jdbc:postgresql://sql.otus.kartushin.su:5432/wishlist";

  private static final String WISHLIST_EDIT_BASE_TITLE = "Travel wishlist";
  private static final String WISHLIST_EDIT_BASE_DESCRIPTION = "Travel wishlist";

  private static final String GIFT_EDIT_BASE_WISHLIST_TITLE = "testingData";
  private static final String GIFT_EDIT_BASE_GIFT_NAME = "Покемон";
  private static final int GIFT_EDIT_BASE_PRICE = 2000;

  private final TestUsersConfig usersConfig;

  @Inject
  public TestDataPreparer(TestUsersConfig usersConfig) {
    this.usersConfig = usersConfig;
  }

  public void prepare(TestDataScenario scenario) {
    String databaseUrl = System.getProperty("databaseUrl", DEFAULT_DATABASE_URL);
    String databaseUsername = System.getProperty("databaseUsername", "");
    String databasePassword = System.getProperty("databasePassword", "");

    System.out.println("databaseUrl = " + databaseUrl);
    System.out.println("databaseUsername = " + databaseUsername);
    System.out.println("databasePassword = " + databasePassword);
    System.out.println("scenario = " + scenario.name());

    try (Connection connection =
        DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
      connection.setAutoCommit(false);

      try {
        switch (scenario) {
          case WISHLIST_CREATE -> prepareWishlistCreate(connection);
          case WISHLIST_EDIT -> prepareWishlistEdit(connection);
          case GIFT_CREATE -> prepareGiftCreate(connection);
          case GIFT_EDIT -> prepareGiftEdit(connection);
          case GIFT_RESERVATION -> prepareGiftReservation(connection);
        }
        connection.commit();
      } catch (Exception exception) {
        connection.rollback();
        throw exception;
      }

    } catch (SQLException exception) {
      throw new IllegalStateException(
          "Cannot prepare test data for scenario: " + scenario, exception);
    }
  }

  private void prepareWishlistCreate(Connection connection) throws SQLException {
    deleteGiftsByUsername(connection, usersConfig.wishlistCreateUser().username());
    deleteWishlistsByUsername(connection, usersConfig.wishlistCreateUser().username());
  }

  private void prepareWishlistEdit(Connection connection) throws SQLException {
    String username = usersConfig.wishlistEditUser().username();

    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            UPDATE wishlists
            SET title = ?, description = ?
            WHERE user_id = (
                SELECT id
                FROM users
                WHERE username = ?
            )
            """)) {
      statement.setString(1, WISHLIST_EDIT_BASE_TITLE);
      statement.setString(2, WISHLIST_EDIT_BASE_DESCRIPTION);
      statement.setString(3, username);
      statement.executeUpdate();
    }
  }

  private void prepareGiftCreate(Connection connection) throws SQLException {
    deleteGiftsByUsername(connection, usersConfig.giftUser().username());
  }

  private void prepareGiftEdit(Connection connection) throws SQLException {
    String username = usersConfig.giftEditUser().username();

    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            UPDATE gifts
            SET name = ?, price = ?, is_reserved = false
            WHERE wish_id IN (
                SELECT w.id
                FROM wishlists w
                JOIN users u ON u.id = w.user_id
                WHERE u.username = ?
                  AND w.title = ?
            )
            """)) {
      statement.setString(1, GIFT_EDIT_BASE_GIFT_NAME);
      statement.setInt(2, GIFT_EDIT_BASE_PRICE);
      statement.setString(3, username);
      statement.setString(4, GIFT_EDIT_BASE_WISHLIST_TITLE);
      statement.executeUpdate();
    }
  }

  private void prepareGiftReservation(Connection connection) throws SQLException {
    String ownerUsername = usersConfig.giftReservationOwnerUser().username();

    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            UPDATE gifts
            SET is_reserved = false
            WHERE wish_id IN (
                SELECT w.id
                FROM wishlists w
                JOIN users u ON u.id = w.user_id
                WHERE u.username = ?
            )
            """)) {
      statement.setString(1, ownerUsername);
      statement.executeUpdate();
    }
  }

  private void deleteGiftsByUsername(Connection connection, String username) throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            DELETE FROM gifts
            WHERE wish_id IN (
                SELECT w.id
                FROM wishlists w
                JOIN users u ON u.id = w.user_id
                WHERE u.username = ?
            )
            """)) {
      statement.setString(1, username);
      statement.executeUpdate();
    }
  }

  private void deleteWishlistsByUsername(Connection connection, String username)
      throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            DELETE FROM wishlists
            WHERE user_id = (
                SELECT id
                FROM users
                WHERE username = ?
            )
            """)) {
      statement.setString(1, username);
      statement.executeUpdate();
    }
  }
}