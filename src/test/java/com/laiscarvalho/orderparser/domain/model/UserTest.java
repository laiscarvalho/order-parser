package com.laiscarvalho.orderparser.domain.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTest {

  @Test
  public void mutationUserTest() {
    var firstUser = User.builder()
        .id(1L)
        .name("Lais")
        .externalId(123L)
        .build();
    var secondUser = User.builder()
        .id(1L)
        .name("Lais")
        .externalId(123L)
        .build();
    assertThat(firstUser).isEqualTo(secondUser);
    assertThat(firstUser.hashCode()).isEqualTo(secondUser.hashCode());
  }
  @Test
  public void mutationValidateIsDiferentUserTest() {
    User firstUser = User.builder()
        .id(1L)
        .externalId(123L)
        .name("Lais")
        .build();

    User secondUser = User.builder()
        .id(2L)
        .externalId(124L)
        .name("Lalais")
        .build();

    assertThat(firstUser).isNotEqualTo(secondUser);
    assertThat(firstUser.hashCode()).isNotEqualTo(secondUser.hashCode());
  }


}
