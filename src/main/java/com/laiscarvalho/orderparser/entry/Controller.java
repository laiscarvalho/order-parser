package com.laiscarvalho.orderparser.entry;

import com.laiscarvalho.orderparser.entry.dto.OrderResponseDto;
import com.laiscarvalho.orderparser.exception.ProcessingErrorType;
import com.laiscarvalho.orderparser.exception.ProcessingException;
import com.laiscarvalho.orderparser.usecase.port.OrderPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "/v1/orders")
@RequestMapping(value = "/v1", produces = {"application/json"})
public class Controller {

  private final OrderPort orderPort;

  @PostMapping(value = "/orders/importer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Realiza o upload de arquivos", method = "POST")
  public void importFile(@Valid @RequestParam("file") MultipartFile multipartFile) throws ProcessingException {
    try {
      log.info("[import-file-controller] file received: {}", multipartFile);
      validateFile(multipartFile);
      orderPort.executeImporter(multipartFile.getInputStream());
    } catch (IOException e) {
      log.error("[import-file-controller] error to process file: {}", multipartFile, e);
      throw new ProcessingException(ProcessingErrorType.INVALID_INPUT_FILE);
    } catch (Exception e) {
      log.error("[import-file-controller] invalid input file: {}", multipartFile, e);
      throw new ProcessingException(ProcessingErrorType.INVALID_INPUT_FILE);
    }
  }

  @GetMapping("/orders")
  @Operation(summary = "Busca todos os pedidos", method = "GET")
  public List<OrderResponseDto> getAllOrders() throws ProcessingException {
    List<OrderResponseDto> orders = orderPort.executeGetOrders();
    if (orders.isEmpty()) {
      log.error("[get-all-orders-controller] get all orders ");
      throw new ProcessingException(ProcessingErrorType.ORDERS_NOT_FOUND);
    }
    return orders;
  }

  @GetMapping("/orders/user/{userId}")
  @Operation(summary = "Busca todos os pedidos por usuario", method = "GET")
  public List<OrderResponseDto> getAllOrdersByUserId(@Valid @PathVariable Long userId) throws ProcessingException {
    List<OrderResponseDto> orders = orderPort.executeGetOrdersByUserId(userId);
    if (orders.isEmpty()) {
      log.error("[get-all-orders-controller] get orders by userId ");
      throw new ProcessingException(ProcessingErrorType.ORDERS_NOT_FOUND);
    }
    return orders;
  }

  private void validateFile(MultipartFile multipartFile) throws ProcessingException {
    String contentType = multipartFile.getContentType();
    String fileName = multipartFile.getOriginalFilename();
    if (!contentType.equals("text/plain") ||
        (!fileName.endsWith(".txt"))) {
      throw new ProcessingException(ProcessingErrorType.INVALID_INPUT_FILE);
    }
  }
}
