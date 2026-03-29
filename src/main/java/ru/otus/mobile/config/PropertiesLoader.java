package ru.otus.mobile.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesLoader {

  private PropertiesLoader() {}

  public static Properties load(String resourceName) {
    Properties properties = new Properties();
    try (InputStream inputStream =
        PropertiesLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
      if (inputStream == null) {
        throw new IllegalStateException("Resource not found: " + resourceName);
      }
      properties.load(inputStream);
      return properties;
    } catch (IOException exception) {
      throw new IllegalStateException("Cannot load resource: " + resourceName, exception);
    }
  }
}
