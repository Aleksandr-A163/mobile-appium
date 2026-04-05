package ru.otus.mobile.driver;

public enum MobileSession {
  EMULATOR_5554("emulator-5554", 4723);

  private final String deviceName;
  private final int appiumPort;

  MobileSession(String deviceName, int appiumPort) {
    this.deviceName = deviceName;
    this.appiumPort = appiumPort;
  }

  public String deviceName() {
    return deviceName;
  }

  public String appiumServerUrl(String appiumHost) {
    String normalizedHost = appiumHost.endsWith("/") ? appiumHost.substring(0, appiumHost.length() - 1) : appiumHost;
    return normalizedHost + ":" + appiumPort;
  }
}