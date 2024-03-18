package io.github.wferdinando.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import io.github.wferdinando.dto.InstituicaoRequestDTO;
import io.github.wferdinando.dto.InstituicaoResponseDTO;
import io.github.wferdinando.service.InstituicaoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/instituicao")
@RequestScoped
public class InstituicaoController {

    @Inject
    InstituicaoService instituicaoService;

    @Operation(summary = "Adicionar Instituição", description = "Recurso para adicionar uma nova Instituição.")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response adicionarInstituicao(InstituicaoRequestDTO instituicaoRequestDTO) {
        InstituicaoResponseDTO novaInstituicao = instituicaoService.adicionarInstituicao(instituicaoRequestDTO);
        return Response.ok(novaInstituicao).build();
    }

    @Operation(summary = "Listar todas as Instituições", description = "Recurso para listar todas Instituições.")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarInstituicoes() {
        List<InstituicaoResponseDTO> instituicoes = instituicaoService.listarInsituicoes();
        return Response.ok(instituicoes).build();
    }

    @Operation(summary = "Buscar Instituição por Id", description = "Recurso para buscar Instituição por Id.")
    @GET
    @Path("/{id}")
    public Response buscarInstituicaoPorId(@PathParam("id") Integer id) {
        InstituicaoResponseDTO instituicao = instituicaoService.buscarInstituicaoPorId(id);
        if (instituicao != null) {
            return Response.ok(instituicao).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @Operation(summary = "Atualizar Instituição", description = "Recurso para atualizar uma Instituição.")
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarInstituicao(@PathParam("id") Integer id, InstituicaoRequestDTO instituicaoDTO) {
        InstituicaoResponseDTO instituicaoAtualizada = instituicaoService.atualizarInstituicao(id, instituicaoDTO);
        if (instituicaoAtualizada != null) {
            return Response.ok(instituicaoAtualizada).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar Instituição", description = "Recurso para deletar uma Instituição.")
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarInstituicao(@PathParam("id") Integer id) {
        boolean deletado = instituicaoService.deletarInstituicao(id);
        if (deletado) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
