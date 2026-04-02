package ru.otus.mobile.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import ru.otus.mobile.config.TestDataConfig;
import ru.otus.mobile.config.TestUsersConfig;

@Singleton
public class TestDataPreparer {

  private static final String DEFAULT_DATABASE_URL =
      "jdbc:postgresql://sql.otus.kartushin.su:5432/wishlist";

  private final TestUsersConfig usersConfig;
  private final TestDataConfig testDataConfig;

  @Inject
  public TestDataPreparer(TestUsersConfig usersConfig, TestDataConfig testDataConfig) {
    this.usersConfig = usersConfig;
    this.testDataConfig = testDataConfig;
  }

  public void prepare(TestDataScenario scenario) {
    String databaseUrl = System.getProperty("databaseUrl", DEFAULT_DATABASE_URL);
    String databaseUsername = System.getProperty("databaseUsername", "");
    String databasePassword = System.getProperty("databasePassword", "");

    try (Connection connection =
        DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
      connection.setAutoCommit(false);

      try {
        System.out.println("Preparing test data for scenario: " + scenario.name());

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
    String username = usersConfig.wishlistCreateUser().username();
    deleteGiftsByUsername(connection, username);
    deleteWishlistsByUsername(connection, username);
  }

  private void prepareWishlistEdit(Connection connection) throws SQLException {
    String username = usersConfig.wishlistEditUser().username();

    deleteGiftsByUsername(connection, username);
    deleteWishlistsByUsername(connection, username);

    createWishlist(
        connection,
        username,
        testDataConfig.wishlistBaseTitle(),
        testDataConfig.wishlistBaseDescription());
  }

  private void prepareGiftCreate(Connection connection) throws SQLException {
    String username = usersConfig.giftUser().username();

    deleteGiftsByUsername(connection, username);

    if (!hasAnyWishlist(connection, username)) {
      createWishlist(
          connection,
          username,
          testDataConfig.wishlistBaseTitle(),
          testDataConfig.wishlistBaseDescription());
    }
  }

  private void prepareGiftEdit(Connection connection) throws SQLException {
    String username = usersConfig.giftEditUser().username();

    deleteGiftsByUsername(connection, username);
    deleteWishlistsByUsername(connection, username);

    UUID wishlistId =
        createWishlist(
            connection,
            username,
            testDataConfig.wishlistBaseTitle(),
            testDataConfig.wishlistBaseDescription());

    insertGift(
        connection,
        wishlistId,
        testDataConfig.giftBaseName(),
        testDataConfig.giftBaseDescription(),
        new BigDecimal(testDataConfig.giftBasePrice()),
        false);
  }

  private void prepareGiftReservation(Connection connection) throws SQLException {
    String ownerUsername = usersConfig.giftReservationOwnerUser().username();

    deleteGiftsByUsername(connection, ownerUsername);
    deleteWishlistsByUsername(connection, ownerUsername);

    UUID wishlistId =
        createWishlist(
            connection,
            ownerUsername,
            testDataConfig.wishlistBaseTitle(),
            testDataConfig.wishlistBaseDescription());

    insertGift(
        connection,
        wishlistId,
        testDataConfig.giftBaseName(),
        testDataConfig.giftBaseDescription(),
        new BigDecimal(testDataConfig.giftBasePrice()),
        false);
  }

  private boolean hasAnyWishlist(Connection connection, String username) throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            SELECT 1
            FROM wishlists w
            JOIN users u ON u.id = w.user_id
            WHERE u.username = ?
            LIMIT 1
            """)) {
      statement.setString(1, username);

      try (ResultSet resultSet = statement.executeQuery()) {
        return resultSet.next();
      }
    }
  }

  private UUID createWishlist(
      Connection connection, String username, String title, String description)
      throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            INSERT INTO wishlists (id, title, description, user_id)
            VALUES (
                ?,
                ?,
                ?,
                (
                    SELECT id
                    FROM users
                    WHERE username = ?
                )
            )
            RETURNING id
            """)) {
      UUID wishlistId = UUID.randomUUID();

      statement.setObject(1, wishlistId);
      statement.setString(2, title);
      statement.setString(3, description);
      statement.setString(4, username);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getObject("id", UUID.class);
        }
      }
    }

    throw new IllegalStateException("Cannot create wishlist for user: " + username);
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

  private void insertGift(
      Connection connection,
      UUID wishlistId,
      String name,
      String description,
      BigDecimal price,
      boolean reserved)
      throws SQLException {
    try (PreparedStatement statement =
        connection.prepareStatement(
            """
            INSERT INTO gifts (id, name, description, price, is_reserved, wish_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """)) {
      statement.setObject(1, UUID.randomUUID());
      statement.setString(2, name);
      statement.setString(3, description);
      statement.setBigDecimal(4, price);
      statement.setBoolean(5, reserved);
      statement.setObject(6, wishlistId);
      statement.executeUpdate();
    }
  }
}
