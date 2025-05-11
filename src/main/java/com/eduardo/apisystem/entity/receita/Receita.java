package com.eduardo.apisystem.entity.receita;

import com.eduardo.apisystem.model.enums.receita.TipoCategoria;
import com.eduardo.apisystem.model.enums.receita.TipoCusto;
import com.eduardo.apisystem.model.enums.receita.TipoDificuldade;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "receita")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long receitaId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao", length = 2500)
    private String descricao;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Ingrediente> ingredienteSet = new LinkedHashSet<>();

    @ElementCollection
    @Column(name = "passo_a_passo")
    private List<String> passoAPasso;

    @Column(name = "tempo")
    private Integer tempo;

    @Column(name = "dificulade")
    private TipoDificuldade tipoDificuldade;

    @Column(name = "pontos_ecologicos")
    private Integer pontosEcologicos;

    @Column(name = "tipo_custo")
    private TipoCusto tipoCusto;

    @Column(name = "custo")
    private BigDecimal custo;

    @Column(name = "categoria")
    private TipoCategoria categoria;

    @Column(name = "autor")
    private String autor;

    @Column(name = "data_hora_criacao")
    private LocalDateTime dataHoraCriacao;

    @PrePersist
    public void prePersist() {
        setDataHoraCriacao(LocalDateTime.now());
    }
}
