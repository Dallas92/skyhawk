package com.example.exception;

import com.example.model.Violation;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {
  private Set<Violation> violations;
}
