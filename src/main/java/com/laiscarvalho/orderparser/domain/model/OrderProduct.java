package com.laiscarvalho.orderparser.domain.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderProduct {
  Long id;
  BigDecimal value;
}
