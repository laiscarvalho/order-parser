package com.laiscarvalho.orderparser.infrastructure.db.repository;

import com.laiscarvalho.orderparser.infrastructure.db.entity.OrderEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  Optional<OrderEntity> findByExternalId(Long externalId);
}
