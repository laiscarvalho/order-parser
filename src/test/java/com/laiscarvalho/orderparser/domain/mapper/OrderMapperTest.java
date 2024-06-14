package com.laiscarvalho.orderparser.domain.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import com.laiscarvalho.orderparser.domain.dto.OrderDto;
import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.domain.model.UserTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

  private final LocalDate LOCAL_DATE =
      LocalDate.of(2024, 6, 10);

    @Test
  public void shouldReturnValidProductDomainToResponseDto() {
    var response = OrderMapper.productDomainToResponseDto(buildProductList());

    assertThat(buildProductList())
        .satisfies(responseProduct -> {
          assertThat(responseProduct.get(0).getId()).isEqualTo(response.get(0).id());
          assertThat(responseProduct.get(0).getValue()).isEqualTo(response.get(0).value());
        });
  }

  @Test
  public void shouldReturnOrderDomainToResponseDto() {

    var response = OrderMapper.orderDomainToResponseDto(List.of(buildOrder()));

    assertThat(buildOrder())
        .satisfies(responseOrder -> {
          assertThat(responseOrder.getId()).isEqualTo(response.get(0).orderId());
          assertThat(responseOrder.getTotalValue()).isEqualTo(response.get(0).total());
          assertThat(responseOrder.getDate()).isEqualTo(response.get(0).date());
          assertThat(responseOrder.getOrderProducts().get(0).getId()).isEqualTo(response.get(0).products().get(0).id());
          assertThat(responseOrder.getOrderProducts().get(0).getValue()).isEqualTo(response.get(0).products().get(0).value());
        });
  }

  @Test
  public void shouldReturnDomainToResponseDto() {
    var response = OrderMapper.domainToResponseDto(List.of(buildOrder()));

    assertThat(buildOrder())
        .satisfies(responseOrder -> {
          assertThat(responseOrder.getId()).isEqualTo(response.get(0).orders().get(0).orderId());
          assertThat(responseOrder.getTotalValue()).isEqualTo(response.get(0).orders().get(0).total());
          assertThat(responseOrder.getDate()).isEqualTo(response.get(0).orders().get(0).date());
          assertThat(responseOrder.getOrderProducts().get(0).getId()).isEqualTo(response.get(0).orders().get(0).products().get(0).id());
          assertThat(responseOrder.getOrderProducts().get(0).getValue()).isEqualTo(response.get(0).orders().get(0).products().get(0).value());
        });
  }

  @Test
  public void shouldReturnDtoToDomain() {

    var response = OrderMapper.dtoToDomain(buildOrderDto());

    assertThat(buildOrder())
        .satisfies(buildOrderDto -> {
          assertThat(buildOrderDto.getId()).isEqualTo(response.getExternalId());
          assertThat(buildOrderDto.getTotalValue()).isEqualTo(response.getTotalValue());
          assertThat(buildOrderDto.getDate()).isEqualTo(response.getDate());
          assertThat(buildOrderDto.getOrderProducts().get(0).getId()).isEqualTo(response.getOrderProducts().get(0).getId());
          assertThat(buildOrderDto.getOrderProducts().get(0).getValue()).isEqualTo(response.getOrderProducts().get(0).getValue());
        });

  }

  public OrderDto buildOrderDto() {
    return OrderDto.builder()
        .orderDate(LOCAL_DATE)
        .orderId(1L)
        .productValue(BigDecimal.valueOf(10))
        .userId(1L)
        .name("Lais")
        .productId(123L)
        .build();
  }

  public User buildUser() {
    return User.builder()
        .name("Lais")
        .externalId(1L)
        .id(1L)
        .build();
  }

  public List<OrderProduct> buildProductList() {
    return List.of(OrderProduct.builder()
        .id(1L)
        .value(BigDecimal.valueOf(10))
        .build());
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


}
