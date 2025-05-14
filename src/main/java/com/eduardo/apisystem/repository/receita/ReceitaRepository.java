package com.eduardo.apisystem.repository.receita;

import com.eduardo.apisystem.entity.receita.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    @Query("FROM Receita WHERE autor = ?1 ORDER BY receitaId DESC")
    List<Receita> buscarReceitaPorUsuario(String email);
}
