package com.eduardo.apisystem.entity.receita;

import com.eduardo.apisystem.model.enums.receita.TipoIngrediente;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingrediente")
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingrediente_id", nullable = false)
    private Long ingredienteId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo_ingrediente")
    private TipoIngrediente tipoIngrediente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receita")
    private Receita receita;

}
