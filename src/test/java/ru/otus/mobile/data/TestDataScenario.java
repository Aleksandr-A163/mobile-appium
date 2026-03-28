package ru.otus.mobile.data;

public enum TestDataScenario {
  WISHLIST_CREATE("SELECT 1;"),
  WISHLIST_EDIT("SELECT 1;"),
  GIFT_CREATE_EDIT("SELECT 1;"),
  GIFT_RESERVATION("SELECT 1;");

  private final String sql;

  TestDataScenario(String sql) {
    this.sql = sql;
  }

  public String sql() {
    return sql;
  }
}
