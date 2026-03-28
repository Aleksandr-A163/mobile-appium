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

    try (Connection connection =
            DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement()) {
      statement.execute(scenario.sql());
    } catch (SQLException exception) {
      throw new IllegalStateException(
          "Cannot prepare test data for scenario: " + scenario, exception);
    }
  }
}
