package io.github.wferdinando.repository;

import java.util.Optional;

import io.github.wferdinando.entity.Instituicao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InstituicaoRepository implements PanacheRepository<Instituicao>{
    public Optional<Instituicao> findByNome(String nome){
        return find("nome", nome).firstResultOptional(); 
    }
}
