package ru.otus.mobile.pages;

import ru.otus.mobile.components.BottomNavComponent;
import ru.otus.mobile.components.TopAppBarComponent;

public abstract class AbsBasePage extends AbsPageObject {

  protected final BottomNavComponent bottomNav = new BottomNavComponent();
  protected final TopAppBarComponent topAppBar = new TopAppBarComponent();

  public void shouldShowToolbar() {
    topAppBar.shouldBeVisible();
  }

  public void shouldShowBottomNavigation() {
    bottomNav.shouldBeVisible();
  }
}
