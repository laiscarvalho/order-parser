package com.laiscarvalho.orderparser.infrastructure.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@Entity
@EqualsAndHashCode(exclude = "orderEntity")
@Table(name = "`product`")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long externalId;

  @ManyToOne
  @JoinColumn(name = "orderId")
  private OrderEntity orderEntity;

  private BigDecimal value;
}
