package ru.otus.mobile.tests;

import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import ru.otus.mobile.config.TestUsersConfig;
import ru.otus.mobile.data.TestDataPreparer;
import ru.otus.mobile.data.TestDataScenario;
import ru.otus.mobile.pages.GiftEditPage;
import ru.otus.mobile.pages.LoginPage;
import ru.otus.mobile.support.MobileTest;

@MobileTest
class GiftTest {

  @Inject private TestUsersConfig usersConfig;
  @Inject private TestDataPreparer testDataPreparer;
  @Inject private LoginPage loginPage;
  @Inject private GiftEditPage giftEditPage;

  @Test
  void shouldCreateAndEditGift() {
    testDataPreparer.prepare(TestDataScenario.GIFT_CREATE_EDIT);
    loginPage.login(usersConfig.giftUser());
    giftEditPage.openCreateGiftForm();
    giftEditPage.createGift("Headphones");
    giftEditPage.openGift("Headphones");
    giftEditPage.editGift("Wireless headphones");
    giftEditPage.shouldContainGift("Wireless headphones");
  }
}
