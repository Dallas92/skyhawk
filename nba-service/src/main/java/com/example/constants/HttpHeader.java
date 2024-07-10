package com.example.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpHeader {
  CONTENT_TYPE("Content-Type");

  private final String value;
}
