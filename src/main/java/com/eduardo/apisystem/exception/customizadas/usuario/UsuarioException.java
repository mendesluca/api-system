package com.eduardo.apisystem.exception.customizadas.usuario;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UsuarioException extends RuntimeException {
  private final HttpStatus status;

  public UsuarioException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
