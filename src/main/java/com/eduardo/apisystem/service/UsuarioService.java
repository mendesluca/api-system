package com.eduardo.apisystem.service;

import com.eduardo.apisystem.entity.Usuario;
import com.eduardo.apisystem.mapper.UsuarioMapper;
import com.eduardo.apisystem.model.dto.usuario.SenhaDTO;
import com.eduardo.apisystem.model.dto.usuario.UsuarioDTO;
import com.eduardo.apisystem.model.dto.usuario.UsuarioResponseDTO;
import com.eduardo.apisystem.repository.UsuarioRepository;
import exception.customizadas.SenhaException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.NormalizedValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;
  private final AuthService authService;
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

  public void atualizarSenha(SenhaDTO senhaNova, String token) {
    Usuario usuario = authService.findUsuarioEntityByToken(token);

    if (senhaNova.getSenha() == null || senhaNova.getSenha().length() < 8) {
      throw new SenhaException("A nova senha não tem mais que 8 caracteres", HttpStatus.BAD_REQUEST);
    }

    usuario.setSenha(passwordEncoder.encode(senhaNova.getSenha()));
    usuarioRepository.save(usuario);
  }

  public UsuarioResponseDTO atualizar(UsuarioDTO usuarioDTO) {
    Usuario usuarioSalvo = usuarioRepository.findById(usuarioDTO.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário com id:" + usuarioDTO.getUsuarioId() + "Não encontrado"));

    Usuario usuarioAtualizar = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);

    usuarioAtualizar.setSenha(usuarioSalvo.getSenha());
    return usuarioMapper.usuarioToUsuarioResponseDTO(usuarioRepository.save(usuarioAtualizar));
  }

  public void deletar(Long usuarioId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario com id:" + usuarioId + "Não encontrado"));

    if (usuario != null) {
      usuarioRepository.delete(usuario);
    }
  }

  public UsuarioResponseDTO buscarUsuarioPorId(Long usuarioId) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário com id:" + usuarioId + "Não encontrado"));
    return usuarioMapper.usuarioToUsuarioResponseDTO(usuario);
  }
}
