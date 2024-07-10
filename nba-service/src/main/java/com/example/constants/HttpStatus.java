package com.example.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStatus {
  OK(200, "OK"),
  CREATED(201, "Created"),
  ACCEPTED(202, "Accepted"),
  NO_CONTENT(204, "No Content"),
  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401, "Unauthorized"),
  FORBIDDEN(403, "Forbidden"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
  SERVICE_UNAVAILABLE(503, "Service Unavailable");

  private final int code;
  private final String reasonPhrase;
}
