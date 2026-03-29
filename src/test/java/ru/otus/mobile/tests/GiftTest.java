package ru.otus.mobile.tests;

import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import ru.otus.mobile.config.TestUsersConfig;
import ru.otus.mobile.data.TestDataPreparer;
import ru.otus.mobile.data.TestDataScenario;
import ru.otus.mobile.pages.GiftEditPage;
import ru.otus.mobile.pages.LoginPage;
import ru.otus.mobile.pages.WishlistPage;
import ru.otus.mobile.support.MobileTest;

@MobileTest
class GiftTest {

  @Inject private TestUsersConfig usersConfig;
  @Inject private TestDataPreparer testDataPreparer;
  @Inject private LoginPage loginPage;
  @Inject private WishlistPage wishlistPage;
  @Inject private GiftEditPage giftEditPage;

  @Test
  void shouldCreateAndEditGift() {
    String giftTitle = "Headphones";
    String updatedGiftTitle = "Wireless headphones";

    testDataPreparer.prepare(TestDataScenario.GIFT_CREATE_EDIT);
    loginPage.login(usersConfig.giftUser());

    wishlistPage.openAnyWishlist();
    giftEditPage.openCreateGiftForm();
    giftEditPage.createGift(giftTitle, 3200, "Created by autotest");
    giftEditPage.shouldContainGift(giftTitle);
    giftEditPage.editGiftByTitle(giftTitle, updatedGiftTitle);
    giftEditPage.shouldContainGift(updatedGiftTitle);
  }

  @Test
  void shouldFixGiftPrice() {
    testDataPreparer.prepare(TestDataScenario.GIFT_RESERVATION);
    loginPage.login(usersConfig.reservationUser());

    wishlistPage.openAnyWishlist();

    int newPrice = giftEditPage.generateRandomPrice(2600, 4100);
    giftEditPage.editFirstGiftPrice(newPrice);
    giftEditPage.shouldContainPrice(newPrice);
  }
}
