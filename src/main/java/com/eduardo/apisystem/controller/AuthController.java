package com.eduardo.apisystem.controller;

import com.eduardo.apisystem.model.dto.TokenDTO;
import com.eduardo.apisystem.model.dto.UsuarioDTO;
import com.eduardo.apisystem.model.request.LoginRequest;
import com.eduardo.apisystem.service.AuthService;
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
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
    TokenDTO tokenDTO = new TokenDTO(authService.authLogin(loginRequest));

    return ResponseEntity.ok().body(tokenDTO);
  }

  @GetMapping("refresh-token/{token}")
  public ResponseEntity<TokenDTO> refreshToken(@PathVariable String token) {
    TokenDTO tokenDTO = new TokenDTO(authService.refreshToken(token));

    return ResponseEntity.ok().body(tokenDTO);
  }
}
