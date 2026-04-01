package ru.otus.mobile.tests;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import ru.otus.mobile.config.TestUsersConfig;
import ru.otus.mobile.data.TestDataPreparer;
import ru.otus.mobile.data.TestDataScenario;
import ru.otus.mobile.pages.FilterUserPage;
import ru.otus.mobile.pages.LoginPage;
import ru.otus.mobile.pages.UserWishlistsPage;
import ru.otus.mobile.pages.UsersPage;
import ru.otus.mobile.pages.WishlistDetailsPage;
import ru.otus.mobile.pages.WishlistPage;
import ru.otus.mobile.support.MobileTest;

@MobileTest
class GiftReservationTest {

  @Inject private TestUsersConfig usersConfig;
  @Inject private TestDataPreparer testDataPreparer;
  @Inject private LoginPage loginPage;
  @Inject private WishlistPage wishlistPage;
  @Inject private UsersPage usersPage;

  @Test
  void shouldChangeReservationStatusForAnotherUserGift() {
    testDataPreparer.prepare(TestDataScenario.GIFT_RESERVATION);
    loginPage.login(usersConfig.giftReservationUser());

    wishlistPage.openUsers();

    FilterUserPage filterUserPage = usersPage.clickFilter();
    UsersPage filteredUsersPage =
        filterUserPage.setUsername(usersConfig.giftReservationOwnerUser().username()).apply();

    UserWishlistsPage userWishlistsPage =
        filteredUsersPage.openUser(usersConfig.giftReservationOwnerUser().username());

    WishlistDetailsPage wishlistDetailsPage = userWishlistsPage.openWishlist(1);

    wishlistDetailsPage.reserveGift(1).shouldShowReservedStatus(1);
  }
}