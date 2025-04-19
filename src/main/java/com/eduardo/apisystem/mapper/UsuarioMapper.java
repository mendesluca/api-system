package com.eduardo.apisystem.mapper;

import com.eduardo.apisystem.entity.Usuario;
import com.eduardo.apisystem.model.dto.UsuarioDTO;
import com.eduardo.apisystem.model.dto.UsuarioResponseDTO;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
  Usuario usuarioDTOtoUsuario(UsuarioDTO usuarioDTO);
  UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
  UsuarioResponseDTO usuarioToUsuarioResponseDTO(Usuario usuario);
  List<Usuario> usuarioDTOListToUsuarioList(List<UsuarioDTO> usuarioDTOList);
  List<UsuarioDTO> usuarioListToUsuarioDTOList(List<Usuario> usuarioList);
}
