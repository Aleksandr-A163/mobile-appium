package ru.otus.mobile.tests;

import com.google.inject.Inject;
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
    String wishlistTitle = "Birthday wishlist " + System.currentTimeMillis();

    testDataPreparer.prepare(TestDataScenario.WISHLIST_CREATE);
    loginPage.login(usersConfig.wishlistCreateUser());
    wishlistPage.openCreateWishlistForm();
    wishlistPage.createWishlist(wishlistTitle);
    wishlistPage.shouldContainWishlist(wishlistTitle);
  }

  @Test
  void shouldEditWishlist() {
    testDataPreparer.prepare(TestDataScenario.WISHLIST_EDIT);
    loginPage.login(usersConfig.wishlistEditUser());

    String updatedTitle = "Updated travel wishlist";

    wishlistPage.editAnyWishlistExcept(updatedTitle, updatedTitle);
    wishlistPage.shouldContainWishlist(updatedTitle);
  }
}
