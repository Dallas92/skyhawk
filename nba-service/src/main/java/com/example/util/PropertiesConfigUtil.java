package com.example.util;

import com.example.Application;
import com.example.config.GlobalConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class PropertiesConfigUtil {
  private static final Logger logger = LoggerFactory.getLogger(PropertiesConfigUtil.class);

  public static void init() throws IOException {
    try (InputStream input =
        Application.class.getClassLoader().getResourceAsStream("application.properties")) {
      Properties prop = new Properties();
      prop.load(input);

      GlobalConfig.DB_URL = replacePlaceholders(prop.getProperty("db.datasource.url"));
      GlobalConfig.DB_USERNAME = replacePlaceholders(prop.getProperty("db.datasource.username"));
      GlobalConfig.DB_PASSWORD = replacePlaceholders(prop.getProperty("db.datasource.password"));

      logger.info("Config properties initialized successfully!");
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      throw ex;
    }
  }

  private static String replacePlaceholders(String value) {
    return value
        .replace("${DB_DATASOURCE_URL}", System.getenv("DB_DATASOURCE_URL"))
        .replace("${DB_DATASOURCE_USERNAME}", System.getenv("DB_DATASOURCE_USERNAME"))
        .replace("${DB_DATASOURCE_PASSWORD}", System.getenv("DB_DATASOURCE_PASSWORD"));
  }
}
