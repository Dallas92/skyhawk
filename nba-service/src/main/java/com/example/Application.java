package com.example;

import com.example.requesthandler.AveragePlayerStatisticsRequestHandler;
import com.example.requesthandler.AverageTeamStatisticsRequestHandler;
import com.example.requesthandler.PlayerStatsRequestHandler;
import com.example.util.DbUtils;
import com.example.util.LocalUtils;
import com.example.util.PropertiesConfigUtil;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import liquibase.exception.LiquibaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws IOException, SQLException, LiquibaseException {
    LocalUtils.init();
    PropertiesConfigUtil.init();
    DbUtils.init();

    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/api/save-player-stats", new PlayerStatsRequestHandler());
    server.createContext("/api/average-player-stats", new AveragePlayerStatisticsRequestHandler());
    server.createContext("/api/average-team-stats", new AverageTeamStatisticsRequestHandler());
    server.setExecutor(null);
    server.start();

    logger.info("Server started on port 8080");
  }
}
