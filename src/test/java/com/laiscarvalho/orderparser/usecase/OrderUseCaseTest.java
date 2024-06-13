package com.laiscarvalho.orderparser.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.laiscarvalho.orderparser.domain.model.Order;
import com.laiscarvalho.orderparser.domain.model.OrderProduct;
import com.laiscarvalho.orderparser.domain.model.User;
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


}
