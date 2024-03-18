package io.github.wferdinando.service;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventoAgendamento {
    @Inject
    EventoService eventoService;

    @Scheduled(cron = "1 1 0 * * ?")
    public void verificarEventosAgendados() {
        eventoService.verificarEventosAtivos();
        System.out.println("Rodando");
    }
}
