package com.laiscarvalho.orderparser.entry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.laiscarvalho.orderparser.entry.dto.OrderResponseDto;
import com.laiscarvalho.orderparser.entry.handler.GlobalExceptionHandler;
import com.laiscarvalho.orderparser.exception.ProcessingErrorType;
import com.laiscarvalho.orderparser.exception.ProcessingException;
import com.laiscarvalho.orderparser.usecase.port.OrderPort;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
  @Mock
  private OrderPort orderPort;

  @InjectMocks
  private Controller controller;

  @Autowired
  private MockMvc mockMvc;

  private final LocalDate LOCAL_DATE =
      LocalDate.of(2024, 6, 10);

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  void shouldReceiveSuccessFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile(
        "file",
        "test.txt",
        "text/plain",
        "Test content".getBytes()
    );

    mockMvc.perform(multipart("/v1/orders/importer")
            .file(multipartFile))
        .andExpect(status().isOk());

    verify(orderPort, times(1)).executeImporter(any(InputStream.class));
  }

  @Test
  void shouldImportFileException() {
    doThrow(new RuntimeException()).when(orderPort).executeImporter(any(InputStream.class));

    MultipartFile multipartFile = new MockMultipartFile(
        "file",
        "test.txt",
        "text/plain",
        "Test content".getBytes()
    );

    ProcessingException exception = assertThrows(
        ProcessingException.class,
        () -> controller.importFile(multipartFile)
    );

    assertThat(exception.getProcessingErrorType()).isEqualTo(ProcessingErrorType.INVALID_INPUT_FILE);
  }

  @Test
  void shouldReceiveInvalidContentTypeFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile(
        "file",
        "test.pdf",
        "text/pla ",
        "Test content".getBytes()
    );

    MvcResult result = mockMvc.perform(multipart("/v1/orders/importer")
            .file(multipartFile))
        .andExpect(status().isBadRequest())
        .andReturn();
    Exception getException = result.getResolvedException();

    assertThat(getException)
        .isInstanceOf(ProcessingException.class)
        .hasMessageContaining("invalid input file");

    verify(orderPort, times(0)).executeImporter(any(InputStream.class));
  }

  @Test
  void shouldReceiveNullContentTypeFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile(
        "file",
        "test.pdf",
        "",
        "Test content".getBytes()
    );

    MvcResult result = mockMvc.perform(multipart("/v1/orders/importer")
            .file(multipartFile))
        .andExpect(status().isBadRequest())
        .andReturn();
    Exception getException = result.getResolvedException();

    assertThat(getException)
        .isInstanceOf(ProcessingException.class)
        .hasMessageContaining("invalid input file");

    verify(orderPort, times(0)).executeImporter(any(InputStream.class));
  }



  @Test
  void shouldReturnResponseException() throws Exception {
    MvcResult result = mockMvc.perform(get("/v1/orders"))
        .andExpect(status().isNotFound())
        .andReturn();

    Exception exception = result.getResolvedException();

    assertThat(exception)
        .isInstanceOf(ProcessingException.class)
        .hasMessageContaining("orders not found");
  }

  @Test
  void shouldReturnResponse() {
    var buildResponse = buildResponse();
    when(orderPort.executeGetOrders()).thenReturn(buildResponse);

    var response = controller.getAllOrders();
    assertThat(response).isNotNull().hasSize(1)
        .element(0)
        .extracting(
            OrderResponseDto::orders, OrderResponseDto::userId, OrderResponseDto::name

        )
        .containsExactly(
            buildResponse.getFirst().orders(),
            buildResponse.getFirst().userId(),
            buildResponse.getFirst().name()
        );
  }

  @Test
  void shouldReturnSuccessResponseByUserId() {
    var buildResponse = buildResponse();
    when(orderPort.executeGetOrdersByUserId(any())).thenReturn(buildResponse);

    var response = controller.getAllOrdersByUserId(any());
    assertThat(response).isNotNull().hasSize(1)
        .element(0)
        .extracting(
            OrderResponseDto::orders, OrderResponseDto::userId, OrderResponseDto::name

        )
        .containsExactly(
            buildResponse.getFirst().orders(),
            buildResponse.getFirst().userId(),
            buildResponse.getFirst().name()
        );
  }

  @Test
  void shouldReturnResponseExceptionByUserId() throws Exception {
    MvcResult result = mockMvc.perform(get("/v1/orders/user/2020"))
        .andExpect(status().isNotFound())
        .andReturn();

    Exception exception = result.getResolvedException();

    assertThat(exception)
        .isInstanceOf(ProcessingException.class)
        .hasMessageContaining("orders not found");
  }


  private List<OrderResponseDto> buildResponse() {
    var product = OrderResponseDto.Product.builder()
        .value(BigDecimal.valueOf(10))
        .id(1L)
        .build();

    var orders = OrderResponseDto.Order.builder()
        .orderId(123L)
        .date(LOCAL_DATE)
        .total(BigDecimal.valueOf(10))
        .products(List.of(product))
        .build();

    return List.of(OrderResponseDto.builder()
        .userId(1L)
        .name("LAIS")
        .orders(List.of(orders))
        .build());
  }

}
