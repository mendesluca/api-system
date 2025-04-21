package com.eduardo.apisystem.model.dto.jwt;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
  private String token;
  private String refreshToken;
}
