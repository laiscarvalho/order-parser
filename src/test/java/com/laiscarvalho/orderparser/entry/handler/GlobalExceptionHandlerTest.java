package com.laiscarvalho.orderparser.entry.handler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import com.laiscarvalho.orderparser.entry.dto.ExceptionResponseDto;
import com.laiscarvalho.orderparser.exception.ProcessingErrorType;
import com.laiscarvalho.orderparser.exception.ProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  public void testHandleProcessingExceptionOrdersNotFound() {
    ProcessingException exception = new ProcessingException(ProcessingErrorType.ORDERS_NOT_FOUND);

    ResponseEntity<Object> response = globalExceptionHandler.handleProcessingException(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isInstanceOf(ExceptionResponseDto.class);

    ExceptionResponseDto body = (ExceptionResponseDto) response.getBody();
    assertThat(body).isNotNull()
        .extracting(
            ExceptionResponseDto::status,
            ExceptionResponseDto::error,
            ExceptionResponseDto::message
        )
        .containsExactly(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.name(),
            "orders not found"
        );
  }

  @Test
  public void testHandleProcessingExceptionInvalidInputFile() {
    ProcessingException exception = new ProcessingException( ProcessingErrorType.INVALID_INPUT_FILE);

    ResponseEntity<Object> response = globalExceptionHandler.handleProcessingException(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isInstanceOf(ExceptionResponseDto.class);

    ExceptionResponseDto body = (ExceptionResponseDto) response.getBody();
    assertThat(body).isNotNull()
        .extracting(
            ExceptionResponseDto::status,
            ExceptionResponseDto::error,
            ExceptionResponseDto::message
        )
        .containsExactly(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.name(),
            "invalid input file"
        );
  }


  @Test
  public void testHandleProcessingExceptionUserOrderNotFound() {
    ProcessingException exception = new ProcessingException( ProcessingErrorType.USER_ORDER_NOT_FOUND);

    ResponseEntity<Object> response = globalExceptionHandler.handleProcessingException(exception);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isInstanceOf(ExceptionResponseDto.class);

    ExceptionResponseDto body = (ExceptionResponseDto) response.getBody();
    assertThat(body).isNotNull()
        .extracting(
            ExceptionResponseDto::status,
            ExceptionResponseDto::error,
            ExceptionResponseDto::message
        )
        .containsExactly(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.name(),
            "user not found"
        );
  }

}
