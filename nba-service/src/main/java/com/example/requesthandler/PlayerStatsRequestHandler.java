package com.example.requesthandler;

import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.exception.BadRequestException;
import com.example.model.SavePlayerStatsRequest;
import com.example.model.Violation;
import com.example.service.DbService;
import com.example.util.HttpResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerStatsRequestHandler implements HttpHandler {
  private static final Logger logger = LoggerFactory.getLogger(PlayerStatsRequestHandler.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    if (HttpMethod.POST.getValue().equals(exchange.getRequestMethod())) {
      try {
        SavePlayerStatsRequest request = getRequest(exchange);
        DbService.getInstance().savePlayerStatistics(request);
        exchange.sendResponseHeaders(HttpStatus.ACCEPTED.getCode(), -1);
      } catch (BadRequestException ex) {
        HttpResponseUtils.prepareBadRequestError(exchange, ex.getViolations());
      } catch (Exception ex) {
        logger.error(ex.getMessage(), ex);
        HttpResponseUtils.prepareInternalServerError(exchange);
      }
    } else {
      exchange.sendResponseHeaders(HttpStatus.METHOD_NOT_ALLOWED.getCode(), -1);
    }
  }

  private SavePlayerStatsRequest getRequest(HttpExchange exchange) throws IOException {
    String requestBody =
        new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    SavePlayerStatsRequest request =
        objectMapper.readValue(requestBody, SavePlayerStatsRequest.class);
    validateRequest(request);
    return request;
  }

  private void validateRequest(SavePlayerStatsRequest savePlayerStatsRequest) {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      Set<Violation> violations =
          factory.getValidator().validate(savePlayerStatsRequest).stream()
              .map(x -> new Violation(x.getPropertyPath().toString(), x.getMessage()))
              .collect(Collectors.toSet());
      if (!violations.isEmpty()) {
        throw new BadRequestException(violations);
      }
    }
  }
}
