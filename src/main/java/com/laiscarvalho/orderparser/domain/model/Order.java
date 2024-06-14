package com.laiscarvalho.orderparser.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Order {
  Long id;
  LocalDate date;
  List<OrderProduct> orderProducts;
  BigDecimal totalValue;
  Long externalId;
  User user;

  public Order addProduct(OrderProduct product) {
    orderProducts.add(product);
    totalValue = totalValue.add(product.getValue());
    return this;
  }

  public static BigDecimal addTotalValue(BigDecimal totalValue, BigDecimal value) {
    return totalValue.add(value);
  }
}