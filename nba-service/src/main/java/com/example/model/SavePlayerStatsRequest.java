package com.example.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePlayerStatsRequest {
  @NotNull private Integer playerId;
  @NotNull private Integer teamId;

  @NotNull
  @Min(value = 0)
  private Integer points;

  @NotNull
  @Min(value = 0)
  private Integer rebounds;

  @NotNull
  @Min(value = 0)
  private Integer assists;

  @NotNull
  @Min(value = 0)
  private Integer steals;

  @NotNull
  @Min(value = 0)
  private Integer blocks;

  @NotNull
  @Min(value = 0)
  @Max(value = 6)
  private Integer fouls;

  @NotNull
  @Min(value = 0)
  private Integer turnovers;

  @NotNull
  @Min(value = 0)
  @Max(value = 48)
  private Float minutesPlayed;
}
