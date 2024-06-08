package com.laiscarvalho.orderparser.infrastructure.db.repository;

import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByExternalId(Long externalId);
}