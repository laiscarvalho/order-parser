package com.laiscarvalho.orderparser.infrastructure.db;

import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.entity.OrderEntity;
import com.laiscarvalho.orderparser.infrastructure.db.mapper.OrderEntityMapper;
import com.laiscarvalho.orderparser.infrastructure.db.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderImp {

  private final OrderRepository orderRepository;
  private final UserImp userImp;

  @Transactional
  public List<Order> updateOrSaveOrderList(Map<Long, Order> orders) {
    return orders.values().stream().map(order -> {
      var foundedOrder = orderRepository.findByExternalId(order.getExternalId());
      var orderEntity = OrderEntityMapper.domainToEntity(order);
      if (foundedOrder.isEmpty()) {
        return saveOrder(orderEntity);
      }
      var existingOrder = foundedOrder.get();
      existingOrder.getProductEntities().addAll(orderEntity.getProductEntities());
      existingOrder.setTotalValue(
          Order.addTotalValue(
            existingOrder.getTotalValue(),
            orderEntity.getProductEntities().getFirst().getValue()));

      return OrderEntityMapper.entityToDomain(orderRepository.save(existingOrder));
    }).toList();
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public Order saveOrder(OrderEntity order) {
    var savedUser = userImp.findUser(order.getUser());
    savedUser.ifPresent(order::setUser);
    var savedOrder = orderRepository.save(order);
    return OrderEntityMapper.entityToDomain(savedOrder);
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll().stream()
        .map(OrderEntityMapper::entityToDomain)
        .toList();
  }

  public List<Order> getOrderByUserId(User user) {
    return orderRepository.findByUserId(user.getId()).stream()
        .map(OrderEntityMapper::entityToDomain)
        .toList();
  }

}
