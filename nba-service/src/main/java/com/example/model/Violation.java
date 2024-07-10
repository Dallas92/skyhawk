package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Violation {
  private String invalidField;
  private String errorDescription;
}
