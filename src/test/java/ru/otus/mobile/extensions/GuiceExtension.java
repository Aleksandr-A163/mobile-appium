package ru.otus.mobile.extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.otus.mobile.di.TestModule;

public class GuiceExtension implements BeforeAllCallback, BeforeEachCallback {

  private static final ExtensionContext.Namespace NAMESPACE =
      ExtensionContext.Namespace.create(GuiceExtension.class);

  @Override
  public void beforeAll(ExtensionContext context) {
    getOrCreateInjector(context);
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    Injector injector = getOrCreateInjector(context);
    Object testInstance = context.getRequiredTestInstance();
    injector.injectMembers(testInstance);
    System.out.println("GUICE injected into: " + testInstance.getClass().getSimpleName());
  }

  private Injector getOrCreateInjector(ExtensionContext context) {
    return context
        .getRoot()
        .getStore(NAMESPACE)
        .getOrComputeIfAbsent(
            "injector", key -> Guice.createInjector(new TestModule()), Injector.class);
  }
}
