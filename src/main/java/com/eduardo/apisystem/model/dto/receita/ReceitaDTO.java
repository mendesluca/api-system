package com.eduardo.apisystem.model.dto.receita;

import com.eduardo.apisystem.model.enums.receita.TipoCategoria;
import com.eduardo.apisystem.model.enums.receita.TipoCusto;
import com.eduardo.apisystem.model.enums.receita.TipoDificuldade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReceitaDTO {
    private Long receitaId;
    private String nome;
    private String descricao;
    private String imagemUrl;
    private Set<IngredienteDTO> ingredienteSet;
    private List<String> passoAPasso;
    private Integer tempo;
    private TipoDificuldade tipoDificuldade;
    private Integer pontosEcologicos;
    private TipoCusto tipoCusto;
    private BigDecimal custo;
    private TipoCategoria categoria;
    private String autor;
    private LocalDateTime dataHoraCriacao;
}
