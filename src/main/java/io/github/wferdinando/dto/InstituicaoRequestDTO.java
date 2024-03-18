package io.github.wferdinando.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.github.wferdinando.entity.Instituicao;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para Cadastro de Instituição")
public record InstituicaoRequestDTO(
    
    @NotNull(message = "Nome da Instituição é Obrigatório")
    @Schema(required = true, description =  "Nome da Instituição", example = "Cresol")
    String nome,

    @NotNull(message = "Tipo da Instituição é Obrigatório")
    @Schema(required = true, description =  "Tipo da Instituição", example = "Cooperativa")
    String tipo
    ) {
    public Instituicao toEntity(){
        return new Instituicao(nome, tipo);
    }
}
