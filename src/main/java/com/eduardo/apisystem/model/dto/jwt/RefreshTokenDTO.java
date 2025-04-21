package com.eduardo.apisystem.model.dto.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenDTO {
    private String refreshToken;
}
