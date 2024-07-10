package com.example.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpHeaderContentType {
  APPLICATION_JSON("application/json");

  private final String value;
}
