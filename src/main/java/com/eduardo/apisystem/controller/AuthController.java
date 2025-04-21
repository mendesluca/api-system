package com.eduardo.apisystem.controller;

import com.eduardo.apisystem.model.dto.jwt.RefreshTokenDTO;
import com.eduardo.apisystem.model.dto.jwt.TokenDTO;
import com.eduardo.apisystem.model.request.LoginRequest;
import com.eduardo.apisystem.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
  private final AuthService authService;

  @PostMapping()
  @Operation(summary = "Realiza o login")
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
    return ResponseEntity.ok().body(authService.authLogin(loginRequest));
  }

  @PostMapping("atualizar-token")
  @Operation(summary = "Atualiza o token")
  public ResponseEntity<TokenDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
    return ResponseEntity.ok().body(authService.refreshToken(refreshTokenDTO));
  }
}
