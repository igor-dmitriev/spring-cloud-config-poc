package com.config.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
  private PropertyUtil() {

  }

  public static void updateProperty(String propertyName, String newValue) throws IOException {
    Properties properties = loadProperties(new File(CustomEnvironmentRepository.PATH));
    properties.setProperty(propertyName, newValue);
    try (FileOutputStream os = new FileOutputStream(CustomEnvironmentRepository.PATH)) {
      properties.store(os, null);
    }
  }


  public static Properties loadProperties(File file) {
    Properties properties = new Properties();
    try {
      try (InputStream inputStream = new FileInputStream(file)) {
        properties.load(inputStream);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties;
  }
}
