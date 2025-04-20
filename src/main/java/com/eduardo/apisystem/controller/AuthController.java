package com.eduardo.apisystem.controller;

import com.eduardo.apisystem.model.dto.jwt.TokenDTO;
import com.eduardo.apisystem.model.request.LoginRequest;
import com.eduardo.apisystem.service.AuthService;
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
    TokenDTO tokenDTO = new TokenDTO(authService.authLogin(loginRequest));

    return ResponseEntity.ok().body(tokenDTO);
  }

  @GetMapping("refresh-token/{token}")
  @Operation(summary = "Atualiza o token")
  public ResponseEntity<TokenDTO> refreshToken(@PathVariable String token) {
    TokenDTO tokenDTO = new TokenDTO(authService.refreshToken(token));

    return ResponseEntity.ok().body(tokenDTO);
  }
}
