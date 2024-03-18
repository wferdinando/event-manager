package io.github.wferdinando.dto;

import java.time.LocalDate;

import io.github.wferdinando.entity.Instituicao;

public record EventoResponseDTO(Integer id, String nome, LocalDate dataInicio, LocalDate dataFim, Instituicao instituicao, Boolean ativo) {
    
}
