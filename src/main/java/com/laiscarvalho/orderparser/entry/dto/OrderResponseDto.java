package com.laiscarvalho.orderparser.entry.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderResponseDto(
    Long userId,
    String name,
    List<Order> orders
) {
  @Builder
  public record Order(
      Long orderId,
      BigDecimal total,
      LocalDate date,
      List<Product> products
  ) {
  }

  @Builder
  public record Product(
      Long id,
      BigDecimal value
  ) {
  }
}