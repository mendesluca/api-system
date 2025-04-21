package com.eduardo.apisystem.entity;

import com.eduardo.apisystem.model.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "perfil")
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Perfil implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id")
    private Long perfilId;

    @Column(name = "tipo_perfil")
    @Enumerated(EnumType.STRING)
    private TipoPerfil tipoPerfil;

    @Override
    public String getAuthority() {
        return "ROLE_" + tipoPerfil;
    }
}
