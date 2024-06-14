package com.laiscarvalho.orderparser.infrastructure.db.mapper;

import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.entity.OrderEntity;
import com.laiscarvalho.orderparser.infrastructure.db.entity.ProductEntity;
import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntityMapper {
  public static Order entityToDomain(OrderEntity orderEntity) {
    var products = orderEntity.getProductEntities().stream()
        .map(productEntity -> OrderProduct.builder()
            .id(productEntity.getExternalId())
            .value(productEntity.getValue())
            .build())
        .collect(Collectors.toList());

    var user = User.builder()
        .name(orderEntity.getUser().getName())
        .id(orderEntity.getUser().getId())
        .externalId(orderEntity.getUser().getExternalId())
        .build();

    return Order.builder()
        .id(orderEntity.getId())
        .date(orderEntity.getOrderDate())
        .externalId(orderEntity.getExternalId())
        .orderProducts(products)
        .user(user)
        .totalValue(orderEntity.getTotalValue())
        .build();
  }

  public static OrderEntity domainToEntity(Order order) {
    var user = UserEntity.builder()
        .externalId(order.getUser().getExternalId())
        .name(order.getUser().getName())
        .id(order.getUser().getId())
        .build();

    var orderEntity = OrderEntity.builder()
        .orderDate(order.getDate())
        .user(user)
        .externalId(order.getExternalId())
        .totalValue(order.getTotalValue())
        .build();

    var products = order.getOrderProducts().parallelStream()
        .map(product -> ProductEntity.builder()
            .externalId(product.getId())
            .value(product.getValue())
            .build())
        .toList();

    orderEntity.setProductEntities(products);

    return orderEntity;
  }
}
