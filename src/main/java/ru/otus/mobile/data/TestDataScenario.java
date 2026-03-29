package ru.otus.mobile.data;

import java.util.UUID;

public enum TestDataScenario {
  WISHLIST_CREATE(
      """
      DELETE FROM gifts
      WHERE wish_id IN (
          SELECT id
          FROM wishlists
          WHERE user_id IN (
              SELECT id
              FROM users
              WHERE username = 'doshick'
          )
      );

      DELETE FROM wishlists
      WHERE user_id IN (
          SELECT id
          FROM users
          WHERE username = 'doshick'
      );
      """),

  WISHLIST_EDIT(
      """
      DELETE FROM gifts
      WHERE wish_id IN (
          SELECT id
          FROM wishlists
          WHERE user_id IN (
              SELECT id
              FROM users
              WHERE username = 'UserEditor'
          )
      );

      DELETE FROM wishlists
      WHERE user_id IN (
          SELECT id
          FROM users
          WHERE username = 'UserEditor'
      );

      INSERT INTO wishlists (id, user_id, description, title)
      SELECT '%s'::uuid, id, 'Travel wishlist', 'Travel wishlist'
      FROM users
      WHERE username = 'UserEditor';

      INSERT INTO wishlists (id, user_id, description, title)
      SELECT '%s'::uuid, id, 'testingData', 'testingData'
      FROM users
      WHERE username = 'UserEditor';
      """),

  GIFT_CREATE(
      """
      DELETE FROM gifts
      WHERE wish_id IN (
          SELECT id
          FROM wishlists
          WHERE user_id IN (
              SELECT id
              FROM users
              WHERE username = 'slowbroo'
          )
      );

      DELETE FROM wishlists
      WHERE user_id IN (
          SELECT id
          FROM users
          WHERE username = 'slowbroo'
      );

      INSERT INTO wishlists (id, user_id, description, title)
      SELECT '%s'::uuid, id, 'Autotest wishlist', 'Autotest wishlist'
      FROM users
      WHERE username = 'slowbroo';
      """),

  GIFT_EDIT(
      """
      DELETE FROM gifts
      WHERE wish_id IN (
          SELECT id
          FROM wishlists
          WHERE user_id IN (
              SELECT id
              FROM users
              WHERE username = 'UserEditor'
          )
      );

      DELETE FROM wishlists
      WHERE user_id IN (
          SELECT id
          FROM users
          WHERE username = 'UserEditor'
      );

      INSERT INTO wishlists (id, user_id, description, title)
      SELECT '%s'::uuid, id, 'Price test wishlist', 'testingData'
      FROM users
      WHERE username = 'UserEditor';

      INSERT INTO gifts (id, wish_id, description, name, price, is_reserved)
      SELECT
          '%s'::uuid,
          w.id,
          'Пикасу и слоупок',
          'Покемон',
          2000,
          false
      FROM wishlists w
      JOIN users u ON u.id = w.user_id
      WHERE u.username = 'UserEditor'
        AND w.title = 'testingData';
      """);

  private final String sql;

  TestDataScenario(String template) {
    this.sql = formatSql(template);
  }

  public String sql() {
    return sql;
  }

  private static String formatSql(String template) {
    int placeholders = countPlaceholders(template);
    Object[] args = new Object[placeholders];

    for (int i = 0; i < placeholders; i++) {
      args[i] = UUID.randomUUID();
    }

    return template.formatted(args);
  }

  private static int countPlaceholders(String text) {
    int count = 0;
    int index = 0;

    while ((index = text.indexOf("%s", index)) >= 0) {
      count++;
      index += 2;
    }

    return count;
  }
}
