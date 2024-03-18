package io.github.wferdinando.service;

import java.util.List;
import java.util.stream.Collectors;

import io.github.wferdinando.dto.InstituicaoRequestDTO;
import io.github.wferdinando.dto.InstituicaoResponseDTO;
import io.github.wferdinando.entity.Instituicao;
import io.github.wferdinando.repository.InstituicaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@ApplicationScoped
public class InstituicaoService {

    @Inject
    InstituicaoRepository instituicaoRepository;

    public InstituicaoResponseDTO adicionarInstituicao(InstituicaoRequestDTO instituicaoRequestDTO) {
        Instituicao novaInstituicao = instituicaoRequestDTO.toEntity();

        novaInstituicao.setNome(instituicaoRequestDTO.nome());
        novaInstituicao.setTipo(instituicaoRequestDTO.tipo());
        instituicaoRepository.persist(novaInstituicao);

        InstituicaoResponseDTO instituicaoResponseDTO = new InstituicaoResponseDTO(
                novaInstituicao.getId(), novaInstituicao.getNome(), novaInstituicao.getTipo());

        return instituicaoResponseDTO;
    }

    public List<InstituicaoResponseDTO> listarInsituicoes() {
        List<Instituicao> insticuioes = instituicaoRepository.findAll().list();
        return insticuioes.stream()
                .map(this::toResponseDTO).collect(Collectors.toList());
    }

    private InstituicaoResponseDTO toResponseDTO(Instituicao instituicao) {
        return new InstituicaoResponseDTO(instituicao.getId(), instituicao.getNome(), instituicao.getTipo());
    }

    public InstituicaoResponseDTO buscarInstituicaoPorId(Integer id) {
        Instituicao instituicao = instituicaoRepository.findById(Long.valueOf(id));
        if (instituicao != null) {
            return new InstituicaoResponseDTO(
                    instituicao.getId(),
                    instituicao.getNome(),
                    instituicao.getTipo());
        } else {
            throw new EntityNotFoundException("Instituição não econtrada!");
        }
    }

    public InstituicaoResponseDTO atualizarInstituicao(Integer id, InstituicaoRequestDTO instituicaoDTO) {
        Instituicao instituicao = instituicaoRepository.findById(Long.valueOf(id));
        if (instituicao != null) {
            instituicao.setNome(instituicaoDTO.nome());
            instituicao.setTipo(instituicaoDTO.tipo());

            instituicaoRepository.persist(instituicao);

            return new InstituicaoResponseDTO(instituicao.getId(), instituicao.getNome(), instituicao.getTipo());
        } else {
            return null;
        }
    }

    public boolean deletarInstituicao(Integer id) {
        Instituicao instituicao = instituicaoRepository.findById(Long.valueOf(id));
        if (instituicao != null) {
            instituicaoRepository.delete(instituicao);
            return true;
        } else {
            return false;
        }
    }

}
