package com.laiscarvalho.orderparser.usecase.port;

import com.laiscarvalho.orderparser.entry.dto.OrderResponseDto;
import java.io.InputStream;
import java.util.List;

public interface OrderPort {
  void executeImporter(InputStream file);

  List<OrderResponseDto> executeGetOrders();
}
