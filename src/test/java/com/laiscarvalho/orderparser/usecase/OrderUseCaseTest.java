package com.laiscarvalho.orderparser.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.entry.dto.OrderResponseDto;
import com.laiscarvalho.orderparser.exception.ProcessingException;
import com.laiscarvalho.orderparser.infrastructure.db.OrderImp;
import com.laiscarvalho.orderparser.infrastructure.db.UserImp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderUseCaseTest {
  @Mock
  private OrderImp orderImp;

  @Mock
  private UserImp userImp;

  @InjectMocks
  private OrderUseCase orderUseCase;

  private final LocalDate LOCAL_DATE =
      LocalDate.of(2024, 6, 10);

  @Test
  public void shouldExecuteImporter() throws IOException {
    String fileName = "src/test/java/com/laiscarvalho/orderparser/files/successFile.txt";
    File file = new File(fileName);
    InputStream stream = new FileInputStream(file);

    when(orderImp.updateOrSaveOrder(any())).thenReturn(any());

    orderUseCase.executeImporter(stream);

    verify(orderImp, times(3)).updateOrSaveOrder(any(Order.class));
  }


  @Test
  public void shouldExecuteWithThrowImporterFile() throws IOException {
    String fileName = "src/test/java/com/laiscarvalho/orderparser/files/errorFile.txt";
    File file = new File(fileName);
    InputStream stream = new FileInputStream(file);

    assertThatThrownBy(() -> {
      orderUseCase.executeImporter(stream);
    })
        .isInstanceOf(ProcessingException.class)
        .hasMessage("file line is not valid");
  }

  @Test
  public void shouldReturnOrderResponseDto() {
    when(orderImp.getAllOrders()).thenReturn(List.of(buildOrder()));

    List<OrderResponseDto> response = orderUseCase.executeGetOrders();

    assertThat(response).isNotNull().hasSize(1)
        .element(0)
        .extracting(
            OrderResponseDto::orders,
            OrderResponseDto::name,
            OrderResponseDto::userId
        )
        .containsExactly(
            buildOrderResponseDto().orders(),
            buildOrderResponseDto().name(),
            buildOrderResponseDto().userId()
            );
  }

  @Test
  public void shouldReturnEmptyOrderResponseDto() {
    when(orderImp.getAllOrders()).thenReturn(null);

    assertThatThrownBy(() -> {
      orderUseCase.executeGetOrders();
    })
        .isInstanceOf(ProcessingException.class)
        .hasMessage("orders not found");
  }

  @Test
  public void shouldReturnOrderByUserIdResponseDto() {
    when(userImp.findUserById(any())).thenReturn(buildUser());
    when(orderImp.getOrderByUserId(any())).thenReturn(List.of(buildOrder()));


    List<OrderResponseDto> response = orderUseCase.executeGetOrdersByUserId(any());
    assertThat(response).isNotNull().hasSize(1)
        .element(0)
        .extracting(
            OrderResponseDto::orders,
            OrderResponseDto::name,
            OrderResponseDto::userId
        )
        .containsExactly(
            buildOrderResponseDto().orders(),
            buildOrderResponseDto().name(),
            buildOrderResponseDto().userId());
  }

  @Test
  public void shouldReturnEmptyUserIdResponseDto() {
    when(userImp.findUserById(any())).thenReturn(null);

    assertThatThrownBy(() -> {
      orderUseCase.executeGetOrdersByUserId(any());
    })
        .isInstanceOf(ProcessingException.class)
        .hasMessage("user not found");
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

  public OrderResponseDto buildOrderResponseDto() {
    return OrderResponseDto.builder()
        .name("Lais")
        .userId(1L)
        .orders(List.of(OrderResponseDto.Order.builder()
            .date(LOCAL_DATE)
            .total(BigDecimal.valueOf(10))
            .orderId(1L)
            .products(List.of(OrderResponseDto.Product.builder()
                .value(BigDecimal.valueOf(10))
                .id(123L)
                .build()))
            .build()))
        .build();
  }
}
