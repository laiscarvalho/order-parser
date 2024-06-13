package com.laiscarvalho.orderparser.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.laiscarvalho.orderparser.domain.model.User;
import com.laiscarvalho.orderparser.infrastructure.db.UserImp;
import com.laiscarvalho.orderparser.infrastructure.db.entity.UserEntity;
import com.laiscarvalho.orderparser.infrastructure.db.mapper.UserEntityMapper;
import com.laiscarvalho.orderparser.infrastructure.db.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserImpTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserImp userImp;

  @Test
  public void shoudReturnFindUser() {
    when(userRepository.findByExternalId(any()))
        .thenReturn(Optional.ofNullable(buildUserEntity()));

    var response = userImp.findUserById(123L);
    assertThat(response).usingRecursiveComparison().isEqualTo(buildUser());

  }

  @Test
  public void shoudReturnNotFoundUser() {
    when(userRepository.findByExternalId(any())).thenReturn(Optional.empty());

    var response = userImp.findUserById(123L);
    assertThat(response).isNull();
  }

  public UserEntity buildUserEntity() {
    return UserEntity.builder()
        .id(1L)
        .name("Lais")
        .externalId(1L)
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
