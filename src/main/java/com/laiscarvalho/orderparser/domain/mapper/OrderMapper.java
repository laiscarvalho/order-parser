package com.laiscarvalho.orderparser.domain.mapper;

import com.laiscarvalho.orderparser.domain.dto.OrderDto;
import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.entry.dto.OrderResponseDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  public static List<OrderResponseDto> domainToResponseDto(List<Order> orders) {

    Map<User, List<Order>> ordersByUser = orders.stream()
        .collect(Collectors.groupingBy(Order::getUser));

    return ordersByUser.entrySet().stream().map(entry ->
        OrderResponseDto.builder()
            .userId(entry.getKey().getExternalId())
            .name(entry.getKey().getName())
            .orders(orderDomainToResponseDto(entry.getValue()))
            .build()

    ).toList();
  }

  public static List<OrderResponseDto.Order> orderDomainToResponseDto(List<Order> orders) {

    return orders.stream().map(order -> OrderResponseDto.Order.builder()
        .orderId(order.getExternalId())
        .total(order.getTotalValue())
        .products(productDomainToResponseDto(order.getOrderProducts()))
        .date(order.getDate())
        .build()).toList();
  }

  public static List<OrderResponseDto.Product> productDomainToResponseDto(List<OrderProduct> products) {
    return products.stream()
        .map(product -> OrderResponseDto.Product.builder()
            .id(product.getId())
            .value(product.getValue())
            .build()).toList();
  }

}
