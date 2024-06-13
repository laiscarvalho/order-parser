package com.laiscarvalho.orderparser.infrastructure.db.mapper;

import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityMapper {

  public static User toDomain(UserEntity userEntity) {
    return User.builder()
        .id(userEntity.getId())
        .name(userEntity.getName())
        .externalId(userEntity.getExternalId())
        .build();
  }
}
