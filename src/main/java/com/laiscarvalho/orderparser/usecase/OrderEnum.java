package com.laiscarvalho.orderparser.usecase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public enum OrderEnum {
  USER_ID(10),
  NAME(45),
  ORDER_ID(10),
  PRODUCT_ID(10),
  PRODUCT_VALUE(12),
  ORDER_DATE(8),
  LINE_SIZE(95);

  private final Integer size;

  public Integer getInitialDataPosition() {
    return switch (this) {
      case USER_ID, LINE_SIZE -> 0;
      case NAME -> USER_ID.getSize();
      case ORDER_ID -> USER_ID.getSize() + NAME.getSize();
      case PRODUCT_ID -> USER_ID.getSize() + NAME.getSize() + ORDER_ID.getSize();
      case PRODUCT_VALUE -> USER_ID.getSize() + NAME.getSize() + ORDER_ID.getSize() + PRODUCT_ID.getSize();
      case ORDER_DATE ->
          USER_ID.getSize() + NAME.getSize() + ORDER_ID.getSize() + PRODUCT_ID.getSize() + PRODUCT_VALUE.getSize();
    };
  }
}