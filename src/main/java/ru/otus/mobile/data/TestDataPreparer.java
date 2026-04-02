package ru.otus.mobile.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import ru.otus.mobile.config.TestUsersConfig;

@Singleton
public class TestDataPreparer {

  private static final String DEFAULT_DATABASE_URL =
      "jdbc:postgresql://sql.otus.kartushin.su:5432/wishlist";

  private static final String WISHLIST_EDIT_BASE_TITLE = "Travel wishlist";
  private static final String WISHLIST_EDIT_BASE_DESCRIPTION = "Travel wishlist";

  private static final String GIFT_EDIT_BASE_WISHLIST_TITLE = "Travel wishlist";
  private static final String GIFT_EDIT_BASE_WISHLIST_DESCRIPTION = "Travel wishlist";
  private static final String GIFT_EDIT_BASE_GIFT_NAME = "Покемон";
  private static final String GIFT_EDIT_BASE_GIFT_DESCRIPTION = "Пикасу и слоупок";
  private static final int GIFT_EDIT_BASE_PRICE = 2000;

  private static final String GIFT_RESERVATION_WISHLIST_TITLE = "Travel wishlist";
  private static final String GIFT_RESERVATION_WISHLIST_DESCRIPTION = "Travel wishlist";
  private static final String GIFT_RESERVATION_GIFT_NAME = "Покемон";
  private static final String GIFT_RESERVATION_GIFT_DESCRIPTION = "Пикасу и слоупок";
  private static final int GIFT_RESERVATION_GIFT_PRICE = 2000;

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

    ensureWishlistExists(
        connection, username, GIFT_EDIT_BASE_WISHLIST_TITLE, GIFT_EDIT_BASE_WISHLIST_DESCRIPTION);

    deleteGiftsByUsernameAndWishlistTitle(connection, username, GIFT_EDIT_BASE_WISHLIST_TITLE);

    insertGiftForWishlist(
        connection,
        username,
        GIFT_EDIT_BASE_WISHLIST_TITLE,
        GIFT_EDIT_BASE_GIFT_NAME,
        GIFT_EDIT_BASE_GIFT_DESCRIPTION,
        GIFT_EDIT_BASE_PRICE);
  }

  private void prepareGiftReservation(Connection connection) throws SQLException {
    String ownerUsername = usersConfig.giftReservationOwnerUser().username();

    ensureWishlistExists(
        connection,
        ownerUsername,
        GIFT_RESERVATION_WISHLIST_TITLE,
        GIFT_RESERVATION_WISHLIST_DESCRIPTION);

    deleteGiftsByUsernameAndWishlistTitle(
        connection, ownerUsername, GIFT_RESERVATION_WISHLIST_TITLE);

    insertGiftForWishlist(
        connection,
        ownerUsername,
        GIFT_RESERVATION_WISHLIST_TITLE,
        GIFT_RESERVATION_GIFT_NAME,
        GIFT_RESERVATION_GIFT_DESCRIPTION,
        GIFT_RESERVATION_GIFT_PRICE);
  }

  private void ensureWishlistExists(
      Connection connection, String username, String wishlistTitle, String wishlistDescription)
      throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            INSERT INTO wishlists (id, user_id, description, title)
            SELECT ?::uuid, u.id, ?, ?
            FROM users u
            WHERE u.username = ?
              AND NOT EXISTS (
                  SELECT 1
                  FROM wishlists w
                  WHERE w.user_id = u.id
                    AND w.title = ?
              )
            """)) {
      statement.setString(1, UUID.randomUUID().toString());
      statement.setString(2, wishlistDescription);
      statement.setString(3, wishlistTitle);
      statement.setString(4, username);
      statement.setString(5, wishlistTitle);
      statement.executeUpdate();
    }
  }

  private void insertGiftForWishlist(
      Connection connection,
      String username,
      String wishlistTitle,
      String giftName,
      String giftDescription,
      int giftPrice)
      throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            INSERT INTO gifts (id, wish_id, description, name, price, is_reserved)
            SELECT ?::uuid, w.id, ?, ?, ?, false
            FROM wishlists w
            JOIN users u ON u.id = w.user_id
            WHERE u.username = ?
              AND w.title = ?
            LIMIT 1
            """)) {
      statement.setString(1, UUID.randomUUID().toString());
      statement.setString(2, giftDescription);
      statement.setString(3, giftName);
      statement.setInt(4, giftPrice);
      statement.setString(5, username);
      statement.setString(6, wishlistTitle);
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

  private void deleteGiftsByUsernameAndWishlistTitle(
      Connection connection, String username, String wishlistTitle) throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            DELETE FROM gifts
            WHERE wish_id IN (
                SELECT w.id
                FROM wishlists w
                JOIN users u ON u.id = w.user_id
                WHERE u.username = ?
                  AND w.title = ?
            )
            """)) {
      statement.setString(1, username);
      statement.setString(2, wishlistTitle);
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
