package com.laiscarvalho.orderparser.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
  Long id;
  Long externalId;
  String name;
}