package com.laiscarvalho.orderparser.exception;

import static com.laiscarvalho.orderparser.exception.ErrorTypeEnum.INVALID_FILE;
import static com.laiscarvalho.orderparser.exception.ErrorTypeEnum.INVALID_LINE;
import lombok.With;


public record ProcessingErrorType(int code, @With String message, ErrorTypeEnum processingErrorType) {
  public static final ProcessingErrorType FILE_INVALID_LINE =
      new ProcessingErrorType(001, "file line is not valid", INVALID_LINE);
  public static final ProcessingErrorType INVALID_INPUT_FILE =
      new ProcessingErrorType(003, "invalid input file", INVALID_FILE);

}