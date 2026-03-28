package ru.otus.mobile.driver;

public final class MobileSessionContext {

  private static final ThreadLocal<MobileSession> CURRENT_SESSION = new ThreadLocal<>();

  private MobileSessionContext() {}

  public static void set(MobileSession session) {
    CURRENT_SESSION.set(session);
  }

  public static MobileSession get() {
    return CURRENT_SESSION.get();
  }

  public static void clear() {
    CURRENT_SESSION.remove();
  }
}
