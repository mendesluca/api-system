package com.eduardo.apisystem.service;

import com.eduardo.apisystem.entity.Usuario;
import com.eduardo.apisystem.mapper.UsuarioMapper;
import com.eduardo.apisystem.model.dto.UsuarioDTO;
import com.eduardo.apisystem.model.dto.UsuarioResponseDTO;
import com.eduardo.apisystem.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;
  private final PasswordEncoder passwordEncoder;

  public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
    Usuario usuario = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);
    usuario.setUsuarioId(null);
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

    return usuarioMapper.usuarioToUsuarioDTO(usuarioRepository.save(usuario));
  }

  public List<UsuarioDTO> buscarTodos() {
    return usuarioMapper.usuarioListToUsuarioDTOList(usuarioRepository.findAll());
  }

  public UsuarioResponseDTO atualizar(UsuarioDTO usuarioDTO) {
    usuarioRepository.findById(usuarioDTO.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuário com id:" + usuarioDTO.getUsuarioId() + "Não encontrado"));

    Usuario usuarioAtualizar = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);

    usuarioAtualizar.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
    return usuarioMapper.usuarioToUsuarioResponseDTO(usuarioRepository.save(usuarioAtualizar));
  }

  public void deletar(Long idUsuario) {
    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Local com id:" + idUsuario + "Não encontrado"));

    if (usuario != null) {
      usuarioRepository.delete(usuario);
    }
  }

  public UsuarioResponseDTO buscarUsuarioPorId(Long idUsuario) {
    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Local com id:" + idUsuario + "Não encontrado"));
    return usuarioMapper.usuarioToUsuarioResponseDTO(usuario);
  }
}
