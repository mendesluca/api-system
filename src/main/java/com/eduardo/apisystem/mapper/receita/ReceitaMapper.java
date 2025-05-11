package com.eduardo.apisystem.mapper.receita;

import com.eduardo.apisystem.entity.receita.Receita;
import com.eduardo.apisystem.model.dto.receita.ReceitaDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceitaMapper {
    Receita toEntity(ReceitaDTO dto);

    ReceitaDTO toDto(Receita entity);

    List<Receita> toListEntity(List<ReceitaDTO> dtoList);

    List<ReceitaDTO> toListDto(List<Receita> entityList);
}
