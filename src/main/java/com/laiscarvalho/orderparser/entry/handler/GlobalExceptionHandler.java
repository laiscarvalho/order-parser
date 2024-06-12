package com.laiscarvalho.orderparser.entry.handler;

import com.laiscarvalho.orderparser.entry.dto.ExceptionResponseDto;
import com.laiscarvalho.orderparser.exception.ProcessingErrorType;
import com.laiscarvalho.orderparser.exception.ProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler extends Throwable {


  @ExceptionHandler(ProcessingException.class)
  public ResponseEntity<Object> handleProcessingException(ProcessingException ex) {
    if (ex.getProcessingErrorType() == ProcessingErrorType.ORDERS_NOT_FOUND) {
      var response = new ExceptionResponseDto(
          HttpStatus.NOT_FOUND.value(),
          HttpStatus.NOT_FOUND.name(),
          ex.getMessage()
      );
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } else if (ex.getProcessingErrorType() == ProcessingErrorType.INVALID_INPUT_FILE) {
      var response = new ExceptionResponseDto(
          HttpStatus.BAD_REQUEST.value(),
          HttpStatus.BAD_REQUEST.name(),
          ex.getMessage()
      );
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } else if (ex.getProcessingErrorType() == ProcessingErrorType.USER_ORDER_NOT_FOUND) {
      var response = new ExceptionResponseDto(
          HttpStatus.NOT_FOUND.value(),
          HttpStatus.NOT_FOUND.name(),
          ex.getMessage()
      );
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    var response = new ExceptionResponseDto(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.name(),
        ex.getMessage()
    );
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}