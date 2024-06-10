package com.laiscarvalho.orderparser.usecase;

import static com.laiscarvalho.orderparser.usecase.OrderEnum.LINE_SIZE;
import static com.laiscarvalho.orderparser.usecase.OrderEnum.NAME;
import static com.laiscarvalho.orderparser.usecase.OrderEnum.ORDER_DATE;
import static com.laiscarvalho.orderparser.usecase.OrderEnum.ORDER_ID;
import static com.laiscarvalho.orderparser.usecase.OrderEnum.PRODUCT_ID;
import static com.laiscarvalho.orderparser.usecase.OrderEnum.PRODUCT_VALUE;
import static com.laiscarvalho.orderparser.usecase.OrderEnum.USER_ID;
import com.laiscarvalho.orderparser.domain.dto.OrderDto;
import com.laiscarvalho.orderparser.domain.mapper.OrderMapper;
import com.laiscarvalho.orderparser.exception.ProcessingErrorType;
import com.laiscarvalho.orderparser.exception.ProcessingException;
import com.laiscarvalho.orderparser.infrastructure.db.OrderImp;
import com.laiscarvalho.orderparser.usecase.port.OrderPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderUseCase implements OrderPort {

  private static final String REMOVE_ZERO = "0";
  private static final Integer DEFAULT_ID_ZERO = 0;

  private final OrderImp orderImp;

  @Override
  public void executeImporter(InputStream file) {
    try (BufferedReader fileBuffered = new BufferedReader(new InputStreamReader(file))) {
      fileBuffered.lines().forEach(line -> {
        var orderDto = formatterLine(line);
        var order = OrderMapper.dtoToDomain(orderDto);
        orderImp.updateOrSaveOrder(order);
      });
    } catch (IOException e) {
      log.error("[execute] Error reading file", e);
    }
  }

  public OrderDto formatterLine(String line) {

    if (!LINE_SIZE.getSize().equals(line.length())) {
      log.error("[formatterLine] Invalid line size: {} ", line.length());
      throw new ProcessingException(ProcessingErrorType.FILE_INVALID_LINE);
    }
    var userId = getDataLine(line, USER_ID);
    var name = getDataLine(line, NAME);
    var orderId = getDataLine(line, ORDER_ID);
    var productId = getDataLine(line, PRODUCT_ID);
    var productValue = getDataLine(line, PRODUCT_VALUE);
    var orderDate = getDataLine(line, ORDER_DATE);

    return OrderDto.builder()
        .orderId(Long.valueOf(orderId))
        .productValue(new BigDecimal(productValue))
        .userId(Long.valueOf(userId))
        .name(name)
        .productId(productId.isEmpty() ? DEFAULT_ID_ZERO : Long.parseLong(productId))
        .orderDate(LocalDate.parse(orderDate, DateTimeFormatter.BASIC_ISO_DATE))
        .build();
  }

  private String getDataLine(String line, OrderEnum rule) {
    return StringUtils.stripStart(line.substring(
            rule.getInitialDataPosition(),
            rule.getInitialDataPosition() + rule.getSize()),
        REMOVE_ZERO).trim();
  }
}
