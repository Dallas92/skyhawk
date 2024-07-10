package com.example.util;

import com.example.config.GlobalConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class DbUtils {
  private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

  public static void init() {
    try (Connection connection =
        DriverManager.getConnection(
            GlobalConfig.DB_URL, GlobalConfig.DB_USERNAME, GlobalConfig.DB_PASSWORD)) {
      try (Liquibase liquibase =
          new Liquibase(
              "db/changelog/db.changelog-master.xml",
              new ClassLoaderResourceAccessor(),
              DatabaseFactory.getInstance()
                  .findCorrectDatabaseImplementation(new JdbcConnection(connection)))) {
        liquibase.update("");
      }

      logger.info("Database initialized successfully!");
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
  }
}
