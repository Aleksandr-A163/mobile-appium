package ru.otus.mobile.config;

public class TestDataConfig {

  public String wishlistBaseTitle() {
    return System.getProperty("testdata.wishlist.base.title", "Travel wishlist");
  }

  public String wishlistBaseDescription() {
    return System.getProperty("testdata.wishlist.base.description", "Travel wishlist");
  }

  public String giftBaseName() {
    return System.getProperty("testdata.gift.base.name", "Покемон");
  }

  public String giftBaseDescription() {
    return System.getProperty("testdata.gift.base.description", "Пикасу и слоупок");
  }

  public String giftBasePrice() {
    return System.getProperty("testdata.gift.base.price", "2000.00");
  }
}
