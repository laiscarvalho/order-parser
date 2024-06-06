package com.laiscarvalho.orderparser.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record OrderDto(Long userId,
                       String name,
                       Long orderId,
                       long productId,
                       BigDecimal productValue,
                       LocalDate orderDate) {

}
