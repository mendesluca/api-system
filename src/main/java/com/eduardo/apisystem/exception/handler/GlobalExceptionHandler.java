package com.eduardo.apisystem.exception.handler;

import com.eduardo.apisystem.exception.customizadas.jwt.TokenJWTException;
import com.eduardo.apisystem.exception.customizadas.receita.ReceitaException;
import com.eduardo.apisystem.exception.customizadas.usuario.SenhaException;
import com.eduardo.apisystem.exception.customizadas.usuario.UsuarioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReceitaException.class)
    public ResponseEntity<ApiError> handlerReceitaException(ReceitaException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ApiError> handlerUsuarioException(UsuarioException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(SenhaException.class)
    public ResponseEntity<ApiError> handlerSenhaException(SenhaException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(TokenJWTException.class)
    public ResponseEntity<ApiError> handlerTokenJWTException(TokenJWTException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handlerGenericException(Exception ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI());

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
