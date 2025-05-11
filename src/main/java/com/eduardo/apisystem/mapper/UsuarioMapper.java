package com.eduardo.apisystem.mapper;

import com.eduardo.apisystem.entity.usuario.Usuario;
import com.eduardo.apisystem.model.dto.usuario.UsuarioDTO;
import com.eduardo.apisystem.model.dto.usuario.UsuarioResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
  Usuario usuarioDTOtoUsuario(UsuarioDTO usuarioDTO);
  UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
  UsuarioResponseDTO usuarioToUsuarioResponseDTO(Usuario usuario);
  List<Usuario> usuarioDTOListToUsuarioList(List<UsuarioDTO> usuarioDTOList);
  List<UsuarioDTO> usuarioListToUsuarioDTOList(List<Usuario> usuarioList);
}
