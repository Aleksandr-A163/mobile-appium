package ru.otus.mobile.driver;

import com.google.inject.Singleton;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Singleton
public class EmulatorPool {

  private final BlockingQueue<MobileSession> sessions = new LinkedBlockingQueue<>();

  public EmulatorPool() {
    for (MobileSession session : MobileSession.values()) {
      sessions.add(session);
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
