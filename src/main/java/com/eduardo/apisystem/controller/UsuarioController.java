package com.eduardo.apisystem.controller;

import com.eduardo.apisystem.model.dto.UsuarioDTO;
import com.eduardo.apisystem.model.dto.UsuarioResponseDTO;
import com.eduardo.apisystem.service.AuthService;
import com.eduardo.apisystem.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/system/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthService authService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder) {
        UsuarioDTO usuario = usuarioService.salvar(usuarioDTO);

        URI uri = uriBuilder.path("/api/system/usuario/{id}").buildAndExpand(usuario.getUsuarioId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> buscarUsuario() {
        return ResponseEntity.ok().body(usuarioService.buscarTodos());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable @NotNull Long usuarioId) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorId(usuarioId));
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable @NotNull Long usuarioId) {
        usuarioService.deletar(usuarioId);
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDTO> atualizar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(usuarioService.atualizar(usuarioDTO));
    }

    @GetMapping("token/{token}")
    public ResponseEntity<UsuarioResponseDTO> loadUserByToken(@PathVariable String token) {
        return ResponseEntity.ok(authService.findUsuarioByToken(token));
    }
}
