package com.laiscarvalho.orderparser.exception;

import static com.laiscarvalho.orderparser.exception.ErrorTypeEnum.INVALID_FILE;
import static com.laiscarvalho.orderparser.exception.ErrorTypeEnum.INVALID_LINE;
import static com.laiscarvalho.orderparser.exception.ErrorTypeEnum.ORDER_NOT_FOUND;
import static com.laiscarvalho.orderparser.exception.ErrorTypeEnum.USER_NOT_FOUND;
import lombok.With;


public record ProcessingErrorType(int code, @With String message, ErrorTypeEnum processingErrorType) {
  public static final ProcessingErrorType FILE_INVALID_LINE =
      new ProcessingErrorType(001, "file line is not valid", INVALID_LINE);
  public static final ProcessingErrorType INVALID_INPUT_FILE =
      new ProcessingErrorType(003, "invalid input file", INVALID_FILE);
  public static final ProcessingErrorType ORDERS_NOT_FOUND =
      new ProcessingErrorType(002, "orders not found", ORDER_NOT_FOUND);
  public static final ProcessingErrorType USER_ORDER_NOT_FOUND =
      new ProcessingErrorType(004, "user not found", USER_NOT_FOUND);

}