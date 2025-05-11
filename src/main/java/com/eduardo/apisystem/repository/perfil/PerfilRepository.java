package com.eduardo.apisystem.repository.perfil;

import com.eduardo.apisystem.entity.usuario.Perfil;
import com.eduardo.apisystem.model.enums.usurio.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    @Query("FROM Perfil WHERE tipoPerfil = ?1")
    Perfil findByNomePerfil(TipoPerfil nome);
}
