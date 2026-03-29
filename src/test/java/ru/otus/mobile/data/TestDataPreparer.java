package ru.otus.mobile.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
public class TestDataPreparer {

  private final DatabaseConfig databaseConfig;

  @Inject
  public TestDataPreparer(DatabaseConfig databaseConfig) {
    this.databaseConfig = databaseConfig;
  }

  public void prepare(TestDataScenario scenario) {
    System.out.println("databaseUrl = " + databaseConfig.url());
    System.out.println("databaseUsername = " + databaseConfig.username());
    System.out.println("databasePassword = " + databaseConfig.password());
    System.out.println("scenario = " + scenario.name());

    try (Connection connection =
            DriverManager.getConnection(
                databaseConfig.url(),
                databaseConfig.username(),
                databaseConfig.password());
        Statement statement = connection.createStatement()) {

      connection.setAutoCommit(false);

      try {
        statement.execute(scenario.sql());
        connection.commit();
      } catch (Exception e) {
        connection.rollback();
        throw e;
      }

    } catch (SQLException e) {
      throw new RuntimeException("Не удалось подготовить данные для сценария: " + scenario.name(), e);
    }
  }
}