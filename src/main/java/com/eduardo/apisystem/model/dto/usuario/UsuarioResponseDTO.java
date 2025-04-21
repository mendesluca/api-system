package com.eduardo.apisystem.model.dto.usuario;

import com.eduardo.apisystem.entity.Perfil;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {
  private Long usuarioId;
  private String email;
  private String login;
  private String nomeCompleto;
  private Set<Perfil> perfilList = new LinkedHashSet<>();
}
