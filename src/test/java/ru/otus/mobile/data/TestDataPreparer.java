package ru.otus.mobile.data;

import com.google.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
public class TestDataPreparer {

  public void prepare(TestDataScenario scenario) {
    String databaseUrl =
        System.getProperty("databaseUrl", "jdbc:postgresql://sql.otus.kartushin.su:5432/wishlist");
    String databaseUsername = System.getProperty("databaseUsername", "");
    String databasePassword = System.getProperty("databasePassword", "");

    System.out.println("databaseUrl = " + databaseUrl);
    System.out.println("databaseUsername = " + databaseUsername);
    System.out.println("databasePassword = " + databasePassword);
    System.out.println("scenario = " + scenario.name());

    try (Connection connection =
            DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement()) {

      connection.setAutoCommit(false);

      try {
        statement.execute(scenario.sql());
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
}
