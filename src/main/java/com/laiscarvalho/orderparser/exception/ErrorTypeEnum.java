package com.laiscarvalho.orderparser.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public enum ErrorTypeEnum {
  INVALID_LINE,
  INVALID_FILE,
  ORDER_NOT_FOUND,
  USER_NOT_FOUND

}
