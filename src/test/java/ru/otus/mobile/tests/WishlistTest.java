package ru.otus.mobile.tests;

import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import ru.otus.mobile.config.TestUsersConfig;
import ru.otus.mobile.data.TestDataPreparer;
import ru.otus.mobile.data.TestDataScenario;
import ru.otus.mobile.pages.LoginPage;
import ru.otus.mobile.pages.WishlistPage;
import ru.otus.mobile.support.MobileTest;

@MobileTest
class WishlistTest {

  @Inject private TestUsersConfig usersConfig;
  @Inject private TestDataPreparer testDataPreparer;
  @Inject private LoginPage loginPage;
  @Inject private WishlistPage wishlistPage;

  @Test
  void shouldCreateWishlist() {
    testDataPreparer.prepare(TestDataScenario.WISHLIST_CREATE);
    loginPage.login(usersConfig.wishlistCreateUser());
    wishlistPage.openCreateWishlistForm();
    wishlistPage.createWishlist("Birthday wishlist");
    wishlistPage.shouldContainWishlist("Birthday wishlist");
  }

  @Test
  void shouldEditWishlist() {
    testDataPreparer.prepare(TestDataScenario.WISHLIST_EDIT);
    loginPage.login(usersConfig.wishlistEditUser());
    wishlistPage.openWishlist("Travel wishlist");
    wishlistPage.editWishlist("Updated travel wishlist");
    wishlistPage.shouldContainWishlist("Updated travel wishlist");
  }
}
