package com.laiscarvalho.orderparser.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
  Long id;
  Long externalId;
  String name;
}