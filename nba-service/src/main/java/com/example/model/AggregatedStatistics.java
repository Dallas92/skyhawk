package com.example.model;

import lombok.*;

@Getter
@Setter
@Builder
public class AggregatedStatistics {
  private Integer avgPoints;
  private Integer avgRebounds;
  private Integer avgAssists;
  private Integer avgSteals;
  private Integer avgBlocks;
  private Integer avgFouls;
  private Integer avgTurnovers;
  private Float avgMinutesPlayed;
}
