package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
  private PropertyUtil() {

  }

  public static void updateProperty(String path, String propertyName, String newValue) throws IOException {
    Properties properties = loadProperties(new File(path));
    properties.setProperty(propertyName, newValue);
    try (FileOutputStream os = new FileOutputStream(path)) {
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
