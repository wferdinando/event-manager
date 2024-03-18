package io.github.wferdinando.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import io.github.wferdinando.dto.EventoRequestDTO;
import io.github.wferdinando.dto.EventoResponseDTO;
import io.github.wferdinando.service.EventoService;
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

@Path("/evento")
public class EventoController {

    @Inject
    EventoService eventoService;

    @Operation(summary = "Adicionar Evento", description = "Recurso para adicionar um novo Evento.")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response adicionarEvento(EventoRequestDTO eventoRequestDTO) {

        EventoResponseDTO novaEvento = eventoService.adicionarEvento(eventoRequestDTO);
        return Response.ok(novaEvento).build();
    }

    @Operation(summary = "Listar todos Eventos", description = "Recurso para litasr todos Eventos.")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEventos() {
        List<EventoResponseDTO> eventos = eventoService.listarEventos();
        return Response.ok(eventos).build();
    }

    @Operation(summary = "Buscar Evento por ID", description = "Recurso para buscar um Evento por ID.")

    @GET
    @Path("/{id}")
    public Response buscarEventoId(@PathParam("id") Integer id) {
        EventoResponseDTO evento = eventoService.buscarEventoPorId(id);
        if (evento != null) {
            return Response.ok(evento).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Atualizar Evento", description = "Recurso para atualizar um Evento por ID.")
    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarEvento(@PathParam("id") Integer id, EventoRequestDTO eventoRequestDTO) {
        EventoResponseDTO eventoAtualizado = eventoService.atualizarEvento(id, eventoRequestDTO);
        if (eventoAtualizado != null) {
            return Response.ok(eventoAtualizado).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletar Evento", description = "Recurso para deletar um Evento por ID.")
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarEvento(@PathParam("id") Integer id) {
        boolean deletado = eventoService.deletarEvento(id);
        if (deletado) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
