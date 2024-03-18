package io.github.wferdinando.dto;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.github.wferdinando.entity.Evento;
import io.github.wferdinando.entity.Instituicao;
import jakarta.validation.constraints.NotNull;

public record EventoRequestDTO(
    @NotNull(message = "Nome do Evento é Obrigatório")
    @Schema(required = true, description =  "Nome do Evento", example = "Evento 1")
    String nome, 
    
    @NotNull(message = "Data de ínicio do evento é obrigatório")
    @Schema(required = true, description =  "Data de início do evento", example = "2024-03-18")
    LocalDate dataInicio, 
    
    @NotNull(message = "Data final do evento é obrigatório")
    @Schema(required = true, description =  "Data final do evento", example = "2024-03-20")
    LocalDate dataFim, 
    
    @NotNull(message = "Uma Instituição é Obrigatório")
    Instituicao instituicao) {
    
    public Evento toEntity(){
        return new Evento(nome, dataInicio, dataFim, instituicao);
    }
}
