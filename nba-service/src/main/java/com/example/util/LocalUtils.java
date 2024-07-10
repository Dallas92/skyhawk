package com.example.util;

import java.util.Locale;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class LocalUtils {
  private static final Logger logger = LoggerFactory.getLogger(LocalUtils.class);

  public static void init() {
    Locale.setDefault(Locale.US);
    logger.info("Locale initialized successfully!");
  }
}
