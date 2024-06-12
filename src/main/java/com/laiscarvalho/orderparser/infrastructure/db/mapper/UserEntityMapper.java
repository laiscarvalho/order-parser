package com.laiscarvalho.orderparser.infrastructure.db.mapper;

import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;

public class UserEntityMapper {

  public static User toDomain(UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .name(userEntity.getName())
        .externalId(userEntity.getExternalId())
        .build();
  }
}
