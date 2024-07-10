package com.example.util;

import com.example.constants.HttpHeader;
import com.example.constants.HttpHeaderContentType;
import com.example.constants.HttpStatus;
import com.example.model.Violation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpResponseUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void prepareBadRequestError(HttpExchange exchange, Set<Violation> violations)
      throws IOException {
    exchange
        .getResponseHeaders()
        .set(HttpHeader.CONTENT_TYPE.getValue(), HttpHeaderContentType.APPLICATION_JSON.getValue());
    exchange.sendResponseHeaders(
        HttpStatus.BAD_REQUEST.getCode(),
        objectMapper.writeValueAsString(violations).getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(objectMapper.writeValueAsString(violations).getBytes());
    os.close();
  }

  public static void prepareInternalServerError(HttpExchange exchange) throws IOException {
    String errorResponse = "Internal Server Error";
    exchange.sendResponseHeaders(
        HttpStatus.INTERNAL_SERVER_ERROR.getCode(), errorResponse.getBytes().length);
    OutputStream os = exchange.getResponseBody();
    os.write(errorResponse.getBytes());
    os.close();
  }
}
