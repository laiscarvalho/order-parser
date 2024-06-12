package com.laiscarvalho.orderparser.infrastructure.db;

import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.mapper.UserEntityMapper;
import com.laiscarvalho.orderparser.infrastructure.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserImp {

  private final UserRepository userRepository;

  public User findUserById(Long userExternalId) {
    return userRepository.findByExternalId(userExternalId)
        .map(UserEntityMapper::toDomain)
        .orElseGet(() -> null);
  }
}
