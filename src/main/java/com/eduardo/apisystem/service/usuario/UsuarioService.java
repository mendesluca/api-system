package com.eduardo.apisystem.service.usuario;

import com.eduardo.apisystem.entity.Perfil;
import com.eduardo.apisystem.entity.Usuario;
import com.eduardo.apisystem.mapper.UsuarioMapper;
import com.eduardo.apisystem.model.dto.perfil.PerfilDTO;
import com.eduardo.apisystem.model.dto.usuario.SenhaDTO;
import com.eduardo.apisystem.model.dto.usuario.UsuarioDTO;
import com.eduardo.apisystem.model.dto.usuario.UsuarioResponseDTO;
import com.eduardo.apisystem.model.enums.TipoPerfil;
import com.eduardo.apisystem.repository.perfil.PerfilRepository;
import com.eduardo.apisystem.repository.usuario.UsuarioRepository;
import com.eduardo.apisystem.service.auth.AuthService;
import com.eduardo.apisystem.service.email.EmailService;
import exception.customizadas.usuario.SenhaException;
import exception.customizadas.usuario.UsuarioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PerfilRepository perfilRepository;
    private final RoleHierarchy roleHierarchy;

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);
        usuario.setUsuarioId(null);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Perfil perfil = perfilRepository.findByNomePerfil(TipoPerfil.USUARIO);
        usuario.setPerfilList(new LinkedHashSet<>());
        usuario.getPerfilList().add(perfil);

        usuario = usuarioRepository.save(usuario);
        emailService.enviarEmailVerificacao(usuario);

        return usuarioMapper.usuarioToUsuarioDTO(usuario);
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
                .orElseThrow(() -> new UsuarioException("Usuário com id:" + usuarioDTO.getUsuarioId() + "Não encontrado", HttpStatus.NOT_FOUND));

        Usuario usuarioAtualizar = usuarioMapper.usuarioDTOtoUsuario(usuarioDTO);

        usuarioAtualizar.setSenha(usuarioSalvo.getSenha());
        return usuarioMapper.usuarioToUsuarioResponseDTO(usuarioRepository.save(usuarioAtualizar));
    }

    public void deletar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioException("Usuario com id:" + usuarioId + "Não encontrado", HttpStatus.NOT_FOUND));

        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioException("Usuário com id:" + usuarioId + "Não encontrado", HttpStatus.NOT_FOUND));
        return usuarioMapper.usuarioToUsuarioResponseDTO(usuario);
    }

    @Transactional
    public String verificarEmail(String codigo) {
        Usuario usuario = usuarioRepository.findByEmailToken(codigo)
                .orElseThrow(() -> new UsuarioException("Código inválido!", HttpStatus.NOT_FOUND));

        if (usuario.getEmailExpiracaoToken().isBefore(LocalDateTime.now())) {
            throw new UsuarioException("Link de verificação expirado", HttpStatus.UNAUTHORIZED);
        }
        usuario.setEmailVerificado(true);
        usuario.setEmailToken(null);
        usuario.setEmailExpiracaoToken(null);

        usuarioRepository.save(usuario);

        return "E-mail verificado com sucesso!";
    }

    @Transactional
    public UsuarioResponseDTO adicionarPerfil(Long usuarioId, PerfilDTO perfilDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioException("Usuário com id:" + usuarioId + "Não encontrado", HttpStatus.NOT_FOUND));

        Perfil perfil = perfilRepository.findByNomePerfil(perfilDTO.getPerfil().getTipoPerfil());

        usuario.getPerfilList().add(perfil);
        usuarioRepository.save(usuario);
        return usuarioMapper.usuarioToUsuarioResponseDTO(usuario);
    }

    private boolean usuarioTemRole(Usuario usuario, String role) {
        return usuario.getAuthorities().stream()
                .flatMap((autoridade) -> {
                    return roleHierarchy.getReachableGrantedAuthorities(List.of(autoridade)).stream();
                })
                .anyMatch((perfil) -> {
                    return perfil.getAuthority().equals(role);
                });
    }
}
