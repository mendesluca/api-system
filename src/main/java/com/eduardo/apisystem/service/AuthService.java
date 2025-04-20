package com.eduardo.apisystem.service;

import com.eduardo.apisystem.entity.Usuario;
import com.eduardo.apisystem.mapper.UsuarioMapper;
import com.eduardo.apisystem.model.dto.usuario.UsuarioResponseDTO;
import com.eduardo.apisystem.model.request.LoginRequest;
import com.eduardo.apisystem.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final TokenService tokenService;
    private final ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login);
    }

    public Usuario findUsuarioEntityByToken(String token) {
        token= token.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);

        return usuarioRepository.findByLogin(subject);
    }

    public UsuarioResponseDTO findUsuarioByToken(String token) {
        token= token.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        Usuario usuario = usuarioRepository.findByLogin(subject);

        return usuarioMapper.usuarioToUsuarioResponseDTO(usuario);
    }

    public String refreshToken(String token) {
        String subject = tokenService.getSubject(token);
        Usuario usuario = usuarioRepository.findByLogin(subject);

        return tokenService.gerarToken(usuario);
    }

    public String authLogin(@Valid LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePassAuthToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getLogin(),
                loginRequest.getSenha()
        );

        AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        Authentication authentication = authenticationManager.authenticate(usernamePassAuthToken);

        return tokenService.gerarToken((Usuario) authentication.getPrincipal());
    }
}


