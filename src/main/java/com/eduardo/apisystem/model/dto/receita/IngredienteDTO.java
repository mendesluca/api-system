package com.eduardo.apisystem.model.dto.receita;


import com.eduardo.apisystem.model.enums.receita.TipoIngrediente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class IngredienteDTO {
    private Long ingredienteId;
    private String nome;
    private TipoIngrediente tipoIngrediente;
}
