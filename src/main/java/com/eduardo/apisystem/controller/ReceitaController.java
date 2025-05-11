package com.eduardo.apisystem.controller;

import com.eduardo.apisystem.model.dto.receita.ReceitaDTO;
import com.eduardo.apisystem.service.receita.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/system/receitas")
public class ReceitaController {
    private final ReceitaService receitaService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Cria uma receita")
    public ResponseEntity<Void> criarUsuario(@RequestBody ReceitaDTO receitaDTO, UriComponentsBuilder uriBuilder, @RequestHeader("Authorization") String token) {
        ReceitaDTO receitaSalva = receitaService.criar(receitaDTO, token);

        URI uri = uriBuilder.path("/api/system/receitas/{id}").buildAndExpand(receitaSalva.getReceitaId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{receitaId}")
    @Operation(summary = "Busca uma receita pelo id")
    public ResponseEntity<ReceitaDTO> handleBuscarPorId(@PathVariable @NotNull Long receitaId) {
        return ResponseEntity.ok().body(receitaService.buscarPorIdDto(receitaId));
    }

    @GetMapping("")
    @Operation(summary = "Busca todas as receitas")
    public ResponseEntity<ReceitaDTO> handleBuscarTodos() {
        return ResponseEntity.ok().body((ReceitaDTO) receitaService.buscarTodos());
    }
}
