package io.github.wferdinando.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import io.github.wferdinando.dto.EventoRequestDTO;
import io.github.wferdinando.dto.EventoResponseDTO;
import io.github.wferdinando.entity.Evento;
import io.github.wferdinando.entity.Instituicao;
import io.github.wferdinando.repository.EventoRepository;
import io.github.wferdinando.repository.InstituicaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EventoService {

    @Inject
    EventoRepository eventoRepository;

    @Inject
    InstituicaoRepository instituicaoRepository;

    public EventoResponseDTO adicionarEvento(EventoRequestDTO eventoRequestDTO) {
        Evento novoEvento = eventoRequestDTO.toEntity();
        Instituicao instituicao = instituicaoRepository.findById(Long.valueOf(eventoRequestDTO.instituicao().getId()));
        this.verificaDatas(eventoRequestDTO.dataInicio(), eventoRequestDTO.dataFim());
        LocalDate now = LocalDate.now();

        if (instituicao != null) {
            novoEvento.setNome(eventoRequestDTO.nome());
            novoEvento.setDataInicial(eventoRequestDTO.dataInicio());
            novoEvento.setDataFinal(eventoRequestDTO.dataFim());
            novoEvento.setInstituicao(instituicao);
            novoEvento.setAtivo(
                    eventoRequestDTO.dataInicio().isEqual(now) || eventoRequestDTO.dataInicio().isBefore(now) ? true
                            : false);

            eventoRepository.persist(novoEvento);

            EventoResponseDTO eventoResponseDTO = new EventoResponseDTO(
                    novoEvento.getId(),
                    novoEvento.getNome(),
                    novoEvento.getDataInicial(),
                    novoEvento.getDataFinal(),
                    novoEvento.getInstituicao(),
                    novoEvento.isAtivo());

            return eventoResponseDTO;
        } else {
            throw new EntityNotFoundException("Não existe nenhuma Instituição cadastrada!");
        }

    }

    public List<EventoResponseDTO> listarEventos() {
        List<Evento> eventos = eventoRepository.findAll().list();
        return eventos.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private EventoResponseDTO toResponseDTO(Evento evento) {
        return new EventoResponseDTO(evento.getId(), evento.getNome(), evento.getDataInicial(), evento.getDataFinal(),
                evento.getInstituicao(), evento.isAtivo());
    }

    public EventoResponseDTO buscarEventoPorId(Integer id) {
        Evento evento = eventoRepository.findById(Long.valueOf(id));
        if (evento != null) {
            return new EventoResponseDTO(
                    evento.getId(),
                    evento.getNome(),
                    evento.getDataInicial(),
                    evento.getDataFinal(),
                    evento.getInstituicao(),
                    evento.isAtivo());
        } else {
            throw new EntityNotFoundException("Evento não econtrado!");
        }
    }

    public EventoResponseDTO atualizarEvento(Integer id, EventoRequestDTO eventoRequestDTO) {
        Evento evento = eventoRepository.findById(Long.valueOf(id));
        if (evento != null) {
            evento.setNome(eventoRequestDTO.nome());
            evento.setInstituicao(eventoRequestDTO.instituicao());
            evento.setDataInicial(eventoRequestDTO.dataInicio());
            evento.setDataFinal(eventoRequestDTO.dataFim());
            eventoRepository.persist(evento);

            return new EventoResponseDTO(evento.getId(), evento.getNome(), evento.getDataInicial(),
                    evento.getDataFinal(), evento.getInstituicao(), evento.isAtivo());
        } else {
            return null;
        }
    }

    public boolean deletarEvento(Integer id) {
        Evento evento = eventoRepository.findById(Long.valueOf(id));
        if (evento != null) {
            eventoRepository.delete(evento);
            return true;
        } else {
            return false;
        }
    }

    private boolean verificaDatas(LocalDate dataInicio, LocalDate dataFinal) {
        LocalDate now = LocalDate.now();
        if (dataFinal.isBefore(now) || dataFinal.isBefore(dataInicio)) {
            throw new RuntimeException("A data Final não pode ser anterior a data de inicio ou a data atual");
        }
        return false;
    }

    @Transactional
    public void verificarEventosAtivos() {
        List<Evento> eventos = eventoRepository.listAll();
        LocalDate now = LocalDate.now();
        for (Evento evento : eventos) {

            if (evento.getDataInicial().isEqual(now)
                    || evento.getDataInicial().isBefore(now) && evento.getDataFinal().isAfter(now)) {
                evento.setAtivo(true);
            } else {
                evento.setAtivo(false);
            }
            eventoRepository.update("ativo = ?1 where id = ?2", evento.isAtivo(), evento.getId());
        }
    }
}
