package com.eduardo.apisystem.repository.receita;

import com.eduardo.apisystem.entity.receita.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
