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
class GiftReservationTest {

  @Inject private TestUsersConfig usersConfig;
  @Inject private TestDataPreparer testDataPreparer;
  @Inject private LoginPage loginPage;
  @Inject private GiftEditPage giftEditPage;

  @Test
  void shouldChangeReservationStatusForAnotherUserGift() {
    testDataPreparer.prepare(TestDataScenario.GIFT_RESERVATION);
    loginPage.login(usersConfig.reservationUser());
    giftEditPage.openGift("Coffee machine");
    giftEditPage.toggleReservation();
  }
}
