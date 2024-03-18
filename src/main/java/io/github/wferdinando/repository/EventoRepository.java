package io.github.wferdinando.repository;

import io.github.wferdinando.entity.Evento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventoRepository implements PanacheRepository<Evento> {
    
}
