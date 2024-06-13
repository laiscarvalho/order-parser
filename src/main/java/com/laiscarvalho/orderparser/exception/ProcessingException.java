package com.laiscarvalho.orderparser.exception;

import lombok.Getter;

@Getter
public class ProcessingException extends RuntimeException {

  private final ProcessingErrorType processingErrorType;
  private Throwable exception;

  public ProcessingException(final ProcessingErrorType processingErrorType) {
    super(processingErrorType.message());
    this.processingErrorType = processingErrorType;
  }
}