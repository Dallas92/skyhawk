package com.example.requesthandler;

import com.example.constants.HttpHeader;
import com.example.constants.HttpHeaderContentType;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.exception.BadRequestException;
import com.example.model.AggregatedStatistics;
import com.example.model.Violation;
import com.example.service.DbService;
import com.example.util.HttpResponseUtils;
import com.example.util.QueryUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AverageTeamStatisticsRequestHandler implements HttpHandler {
  private static final Logger logger =
      LoggerFactory.getLogger(AverageTeamStatisticsRequestHandler.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    if (HttpMethod.GET.getValue().equals(exchange.getRequestMethod())) {
      try {
        Integer id = getId(exchange);
        AggregatedStatistics aggregatedStatistics = DbService.getInstance().getTeamStatistics(id);
        exchange
            .getResponseHeaders()
            .set(
                HttpHeader.CONTENT_TYPE.getValue(),
                HttpHeaderContentType.APPLICATION_JSON.getValue());
        exchange.sendResponseHeaders(
            HttpStatus.OK.getCode(),
            objectMapper.writeValueAsString(aggregatedStatistics).length());
        OutputStream os = exchange.getResponseBody();
        os.write(objectMapper.writeValueAsString(aggregatedStatistics).getBytes());
        os.close();
      } catch (Exception ex) {
        logger.error(ex.getMessage(), ex);
        HttpResponseUtils.prepareInternalServerError(exchange);
      }
    } else {
      exchange.sendResponseHeaders(HttpStatus.METHOD_NOT_ALLOWED.getCode(), -1);
    }
  }

  private Integer getId(HttpExchange exchange) {
    Map<String, String> queryParams = QueryUtils.queryToMap(exchange.getRequestURI().getQuery());
    String teamId = queryParams.get("teamId");
    if (StringUtil.isEmpty(teamId)) {
      throw new BadRequestException(Set.of(new Violation("teamId", "must not be null")));
    }
    return Integer.parseInt(teamId);
  }
}
