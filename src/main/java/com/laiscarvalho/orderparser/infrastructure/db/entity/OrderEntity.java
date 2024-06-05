package com.laiscarvalho.orderparser.infrastructure.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "`order`")
public class OrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long externalId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userId")
  private UserEntity user;

  private LocalDate orderDate;

  public BigDecimal totalValue;

  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductEntity> productEntities;
}