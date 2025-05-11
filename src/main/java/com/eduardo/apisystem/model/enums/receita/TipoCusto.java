package com.eduardo.apisystem.model.enums.receita;

import lombok.Getter;

@Getter
public enum TipoCusto {
    ECONOMICO, // 0 a 20
    BARATO, // 20 a 40
    RAZOAVEL, // 40 a 60
    CARO // acima de 60
}
