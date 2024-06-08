package com.laiscarvalho.orderparser.domain.mapper;

import com.laiscarvalho.orderparser.domain.dto.OrderDto;
import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
import java.util.List;

public class OrderMapper {

  public static Order dtoToDomain(OrderDto order) {
    List<OrderProduct> products = List.of(OrderProduct.builder()
        .value(order.productValue())
        .id(order.productId())
        .build());

    return Order.builder()
        .date(order.orderDate())
        .externalId(order.orderId())
        .orderProducts(products)
        .totalValue(products.getFirst().getValue())
        .user(User.builder()
            .name(order.name())
            .externalId(order.userId())
            .build())
        .build();
  }
}
