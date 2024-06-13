package com.laiscarvalho.orderparser.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.OrderImp;
import com.laiscarvalho.orderparser.infrastructure.db.entity.OrderEntity;
import com.laiscarvalho.orderparser.infrastructure.db.entity.ProductEntity;
import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;
import com.laiscarvalho.orderparser.infrastructure.db.repository.OrderRepository;
import com.laiscarvalho.orderparser.infrastructure.db.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderImplTest {
  @Mock
  private OrderRepository orderRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private OrderImp orderImp;

  private final LocalDate LOCAL_DATE =
      LocalDate.of(2024, 6, 10);

  @Test
  public void shouldUpdateOrSaveOrderFindExistingOrder() {
    when(orderRepository.findByExternalId(any()))
        .thenReturn(Optional.ofNullable(buildOrderEntity()));

    when(orderRepository.save(any())).thenReturn(buildOrderEntity());
    var response = orderImp.updateOrSaveOrder(buildOrder());
    assertThat(response).usingRecursiveComparison()
        .isEqualTo(buildOrder());
  }

  @Test
  public void shouldUpdateOrSaveOrderNewOrder() {
    when(orderRepository.findByExternalId(any()))
        .thenReturn(Optional.empty());

    when(orderRepository.save(any())).thenReturn(buildOrderEntity());
    var response = orderImp.updateOrSaveOrder(buildOrder());
    assertThat(response).usingRecursiveComparison()
        .isEqualTo(buildOrder());
  }

  @Test
  public void shouldNotFoundOrders() {
    when(orderRepository.findAll()).thenReturn(List.of());
    List<Order> response = orderImp.getAllOrders();
    assertThat(response).isEmpty();
  }

  @Test
  public void shouldSuccessAllOrders() {
    when(orderRepository.findAll())
        .thenReturn(List.of(buildOrderEntity()));
    List<Order> response = orderImp.getAllOrders();

    assertThat(response.getFirst())
        .usingRecursiveComparison().isEqualTo(buildOrder());
  }

  @Test
  public void shouldSuccessOrderByUserId() {
    when(orderRepository.findByUserId(anyLong()))
        .thenReturn(List.of(buildOrderEntity()));

    List<Order> response = orderImp.getOrderByUserId(buildUser());
    assertThat(response.getFirst())
        .usingRecursiveComparison().isEqualTo(buildOrder());
  }

  @Test
  public void shouldNotFoundOrderByUserId() {
    when(orderRepository.findByUserId(anyLong())).thenReturn(List.of());
    List<Order> response = orderImp.getOrderByUserId(buildUser());
    assertThat(response).isEmpty();
  }

  public OrderEntity buildOrderEntity() {
    return OrderEntity.builder()
        .totalValue(BigDecimal.valueOf(10))
        .orderDate(LOCAL_DATE)
        .user(buildUserEntity())
        .productEntities(List.of(buildProductEntity()))
        .id(1L)
        .externalId(1L)
        .build();
  }

  public ProductEntity buildProductEntity() {
    return ProductEntity.builder()
        .id(123L)
        .value(BigDecimal.valueOf(10))
        .externalId(123L)
        .build();
  }

  public Order buildOrder() {
    List<OrderProduct> products = List.of(OrderProduct.builder()
        .value(BigDecimal.valueOf(10))
        .id(123L)
        .build());

    return Order.builder()
        .totalValue(BigDecimal.valueOf(10))
        .date(LOCAL_DATE)
        .orderProducts(products)
        .externalId(1L)
        .id(1L)
        .user(buildUser())
        .build();
  }

  public User buildUser() {
    return User.builder()
        .name("Lais")
        .externalId(1L)
        .id(1L)
        .build();
  }

  public UserEntity buildUserEntity() {
    return UserEntity.builder()
        .name("Lais")
        .externalId(1L)
        .id(1L)
        .build();
  }
}
