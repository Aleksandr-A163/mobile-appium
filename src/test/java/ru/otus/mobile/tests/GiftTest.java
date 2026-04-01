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
    String createdGiftTitle = "Steam Deck";
    String updatedGiftTitle = "Steam Deck OLED";

    testDataPreparer.prepare(TestDataScenario.GIFT_CREATE);
    loginPage.login(usersConfig.giftUser());

    wishlistPage.checkOpened();
    wishlistPage.openAnyWishlist();

    giftEditPage.checkOpened();
    giftEditPage.openCreateGiftForm();
    giftEditPage.createGift(createdGiftTitle);
    giftEditPage.shouldContainGift(createdGiftTitle);

    giftEditPage.editGiftByTitle(createdGiftTitle, updatedGiftTitle);
    giftEditPage.shouldContainGift(updatedGiftTitle);
  }

  @Test
  void shouldFixGiftPrice() {
    int newPrice;

    testDataPreparer.prepare(TestDataScenario.GIFT_EDIT);
    loginPage.login(usersConfig.giftEditUser());

    wishlistPage.openWishlist("testingData");
    giftEditPage.checkOpened();

    newPrice = giftEditPage.generateRandomPrice(2600, 4100);
    giftEditPage.editFirstGiftPrice(newPrice);
    giftEditPage.shouldContainPrice(newPrice);
  }
}
