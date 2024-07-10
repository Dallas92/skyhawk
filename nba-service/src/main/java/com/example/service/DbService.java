package com.example.service;

import com.example.config.GlobalConfig;
import com.example.model.AggregatedStatistics;
import com.example.model.SavePlayerStatsRequest;
import java.sql.*;

public class DbService {
  private static volatile DbService instance;

  private DbService() {}

  public static DbService getInstance() {
    if (instance == null) {
      synchronized (DbService.class) {
        if (instance == null) {
          instance = new DbService();
        }
      }
    }
    return instance;
  }

  public void savePlayerStatistics(SavePlayerStatsRequest savePlayerStatsRequest)
      throws SQLException {
    try (Connection connection =
        DriverManager.getConnection(
            GlobalConfig.DB_URL, GlobalConfig.DB_USERNAME, GlobalConfig.DB_PASSWORD)) {

      String sql =
          """
                            INSERT INTO player_stats (player_id, team_id, points, rebounds, assists, steals, blocks, fouls, turnovers, minutes_played)
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                            """;

      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, savePlayerStatsRequest.getPlayerId());
        preparedStatement.setInt(2, savePlayerStatsRequest.getTeamId());
        preparedStatement.setInt(3, savePlayerStatsRequest.getPoints());
        preparedStatement.setInt(4, savePlayerStatsRequest.getRebounds());
        preparedStatement.setInt(5, savePlayerStatsRequest.getAssists());
        preparedStatement.setInt(6, savePlayerStatsRequest.getSteals());
        preparedStatement.setInt(7, savePlayerStatsRequest.getBlocks());
        preparedStatement.setInt(8, savePlayerStatsRequest.getFouls());
        preparedStatement.setInt(9, savePlayerStatsRequest.getTurnovers());
        preparedStatement.setFloat(10, savePlayerStatsRequest.getMinutesPlayed());

        preparedStatement.executeUpdate();
      }
    }
  }

  public AggregatedStatistics getPlayerStatistics(Integer playerId) throws SQLException {
    try (Connection connection =
        DriverManager.getConnection(
            GlobalConfig.DB_URL, GlobalConfig.DB_USERNAME, GlobalConfig.DB_PASSWORD)) {

      String sql =
          """
                            SELECT
                                AVG(points) AS avg_points,
                                AVG(rebounds) AS avg_rebounds,
                                AVG(assists) AS avg_assists,
                                AVG(steals) AS avg_steals,
                                AVG(blocks) AS avg_blocks,
                                AVG(fouls) AS avg_fouls,
                                AVG(turnovers) AS avg_turnovers,
                                AVG(minutes_played) AS avg_minutes_played
                            FROM player_stats
                            WHERE player_id = ?
                            GROUP BY player_id
                            """;

      return getAggregatedStatistics(playerId, connection, sql);
    }
  }

  public AggregatedStatistics getTeamStatistics(Integer teamId) throws SQLException {
    try (Connection connection =
        DriverManager.getConnection(
            GlobalConfig.DB_URL, GlobalConfig.DB_USERNAME, GlobalConfig.DB_PASSWORD)) {

      String sql =
          """
                            SELECT
                                AVG(points) AS avg_points,
                                AVG(rebounds) AS avg_rebounds,
                                AVG(assists) AS avg_assists,
                                AVG(steals) AS avg_steals,
                                AVG(blocks) AS avg_blocks,
                                AVG(fouls) AS avg_fouls,
                                AVG(turnovers) AS avg_turnovers,
                                AVG(minutes_played) AS avg_minutes_played
                            FROM player_stats
                            WHERE team_id = ?
                            GROUP BY team_id
                            """;

      return getAggregatedStatistics(teamId, connection, sql);
    }
  }

  private AggregatedStatistics getAggregatedStatistics(
      Integer playerId, Connection connection, String sql) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, playerId);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return AggregatedStatistics.builder()
            .avgPoints(resultSet.getInt("avg_points"))
            .avgRebounds(resultSet.getInt("avg_rebounds"))
            .avgAssists(resultSet.getInt("avg_assists"))
            .avgSteals(resultSet.getInt("avg_steals"))
            .avgBlocks(resultSet.getInt("avg_blocks"))
            .avgFouls(resultSet.getInt("avg_fouls"))
            .avgTurnovers(resultSet.getInt("avg_turnovers"))
            .avgMinutesPlayed(resultSet.getFloat("avg_minutes_played"))
            .build();
      } else {
        return AggregatedStatistics.builder()
            .avgPoints(0)
            .avgRebounds(0)
            .avgAssists(0)
            .avgSteals(0)
            .avgBlocks(0)
            .avgFouls(0)
            .avgTurnovers(0)
            .avgMinutesPlayed(0F)
            .build();
      }
    }
  }
}
