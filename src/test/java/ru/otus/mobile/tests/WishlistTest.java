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
    String wishlistTitle = "Birthday wishlist";

    testDataPreparer.prepare(TestDataScenario.WISHLIST_CREATE);
    loginPage.login(usersConfig.wishlistCreateUser());

    wishlistPage.checkOpened();
    wishlistPage.openCreateWishlistForm();
    wishlistPage.createWishlist(wishlistTitle);
    wishlistPage.shouldContainWishlist(wishlistTitle);
  }

  @Test
  void shouldEditWishlist() {
    String originalTitle = "Travel wishlist";
    String updatedTitle = "Updated travel wishlist";

    testDataPreparer.prepare(TestDataScenario.WISHLIST_EDIT);
    loginPage.login(usersConfig.wishlistEditUser());

    wishlistPage.checkOpened();
    wishlistPage.openWishlistForEdit(originalTitle);
    wishlistPage.createWishlist(updatedTitle);
    wishlistPage.shouldContainWishlist(updatedTitle);
  }
}