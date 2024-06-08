package com.laiscarvalho.orderparser.infrastructure.db;

import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.infrastructure.db.entity.OrderEntity;
import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;
import com.laiscarvalho.orderparser.infrastructure.db.mapper.OrderEntityMapper;
import com.laiscarvalho.orderparser.infrastructure.db.repository.OrderRepository;
import com.laiscarvalho.orderparser.infrastructure.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderImp {

  private final OrderRepository orderRepository;
  private final UserRepository userRepository;

  @Transactional
  public Order updateOrSaveOrder(Order order) {
    var foundedOrder = orderRepository.findByExternalId(order.getExternalId());
    var orderUpdated = findOrSaveUser(order);
    if (foundedOrder.isEmpty()) {
      return saveOrder(orderUpdated);
    }
    var existingOrder = foundedOrder.get();
    var updatedProducts = new ArrayList<>(orderUpdated.getProductEntities());
    updatedProducts.forEach(product -> product.setOrderEntity(existingOrder));
    existingOrder.setProductEntities(updatedProducts);
    return OrderEntityMapper.entityToDomain(orderRepository.save(existingOrder));
  }

  @Transactional
  public Order saveOrder(OrderEntity order) {
    var savedOrder = orderRepository.save(order);
    return OrderEntityMapper.entityToDomain(savedOrder);
  }

  @Transactional
  public OrderEntity findOrSaveUser(Order order) {
    OrderEntity orderEntity = OrderEntityMapper.domainToEntity(order);
    UserEntity user = orderEntity.getUser();
    Optional<UserEntity> existingUser = userRepository.findByExternalId(user.getExternalId());
    if (existingUser.isPresent()) {
      orderEntity.setUser(existingUser.get());
    } else {
      userRepository.save(user);
    }
    return orderEntity;
  }

}
