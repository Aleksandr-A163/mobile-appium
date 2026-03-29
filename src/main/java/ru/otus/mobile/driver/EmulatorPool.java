package ru.otus.mobile.driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import ru.otus.mobile.config.TestConfig;

@Singleton
public class EmulatorPool {

  private final BlockingQueue<MobileSession> sessions;

  @Inject
  public EmulatorPool(TestConfig config) {
    List<String> deviceNames = config.deviceNames();
    List<String> serverUrls = config.serverUrls();
    if (deviceNames.size() != serverUrls.size()) {
      throw new IllegalStateException("device.names and server.urls must have the same size");
    }
    sessions = new LinkedBlockingQueue<>();
    for (int i = 0; i < deviceNames.size(); i++) {
      sessions.add(new MobileSession(deviceNames.get(i), serverUrls.get(i)));
    }
  }

  public MobileSession take() {
    try {
      return sessions.take();
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Interrupted while waiting for free emulator", exception);
    }
  }

  public void release(MobileSession session) {
    sessions.offer(session);
  }
}
