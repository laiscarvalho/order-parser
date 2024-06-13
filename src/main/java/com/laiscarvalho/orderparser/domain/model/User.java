package com.laiscarvalho.orderparser.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
@EqualsAndHashCode
@Getter
@Builder
public class User {
  Long id;
  Long externalId;
  String name;
}