package ru.otus.mobile.data;

public enum TestDataScenario {
  WISHLIST_CREATE("""
      -- Очистка данных пользователя для сценария создания wishlist
      -- ЗАМЕНИ user_id / username / email на реальные поля вашей БД

      DELETE FROM gifts
      WHERE wishlist_id IN (
          SELECT id
          FROM wishlists
          WHERE owner_id = (
              SELECT id FROM users WHERE username = 'wishlist_create_user'
          )
      );

      DELETE FROM wishlists
      WHERE owner_id = (
          SELECT id FROM users WHERE username = 'wishlist_create_user'
      );
      """),

  WISHLIST_EDIT("""
      -- Полный reset wishlist для пользователя редактирования

      DELETE FROM gifts
      WHERE wishlist_id IN (
          SELECT id
          FROM wishlists
          WHERE owner_id = (
              SELECT id FROM users WHERE username = 'wishlist_edit_user'
          )
      );

      DELETE FROM wishlists
      WHERE owner_id = (
          SELECT id FROM users WHERE username = 'wishlist_edit_user'
      );

      INSERT INTO wishlists (id, owner_id, title, description)
      VALUES (
          nextval('wishlists_id_seq'),
          (SELECT id FROM users WHERE username = 'wishlist_edit_user'),
          'Travel wishlist',
          'Created for edit test'
      );

      INSERT INTO wishlists (id, owner_id, title, description)
      VALUES (
          nextval('wishlists_id_seq'),
          (SELECT id FROM users WHERE username = 'wishlist_edit_user'),
          'testingData',
          'Second wishlist for edit test'
      );
      """),

  GIFT_CREATE_EDIT("""
      -- У пользователя giftUser должен быть один стабильный wishlist без мусора

      DELETE FROM gifts
      WHERE wishlist_id IN (
          SELECT id
          FROM wishlists
          WHERE owner_id = (
              SELECT id FROM users WHERE username = 'gift_user'
          )
      );

      DELETE FROM wishlists
      WHERE owner_id = (
          SELECT id FROM users WHERE username = 'gift_user'
      );

      INSERT INTO wishlists (id, owner_id, title, description)
      VALUES (
          nextval('wishlists_id_seq'),
          (SELECT id FROM users WHERE username = 'gift_user'),
          'Autotest wishlist',
          'Wishlist for gift create/edit test'
      );
      """),

  GIFT_RESERVATION("""
      -- Для теста цены / резервации у пользователя должен быть один wishlist и один gift

      DELETE FROM gifts
      WHERE wishlist_id IN (
          SELECT id
          FROM wishlists
          WHERE owner_id = (
              SELECT id FROM users WHERE username = 'reservation_user'
          )
      );

      DELETE FROM wishlists
      WHERE owner_id = (
          SELECT id FROM users WHERE username = 'reservation_user'
      );

      INSERT INTO wishlists (id, owner_id, title, description)
      VALUES (
          nextval('wishlists_id_seq'),
          (SELECT id FROM users WHERE username = 'reservation_user'),
          'Price test wishlist',
          'Wishlist for price test'
      );

      INSERT INTO gifts (id, wishlist_id, title, description, price, reserved)
      VALUES (
          nextval('gifts_id_seq'),
          (
              SELECT id
              FROM wishlists
              WHERE owner_id = (
                  SELECT id FROM users WHERE username = 'reservation_user'
              )
              ORDER BY id DESC
              LIMIT 1
          ),
          'Autotest gift',
          'Gift for price test',
          2000,
          false
      );
      """);

  private final String sql;

  TestDataScenario(String sql) {
    this.sql = sql;
  }

  public String sql() {
    return sql;
  }
}