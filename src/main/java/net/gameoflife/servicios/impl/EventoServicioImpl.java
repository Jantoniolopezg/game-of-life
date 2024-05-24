package net.gameoflife.servicios.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.eventos.Evento;
import net.gameoflife.eventos.IndividuoEvento;
import net.gameoflife.servicios.EventoServicio;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventoServicioImpl implements EventoServicio {

    @Getter
    @Setter
    private List<Evento> eventos = new ArrayList<>();

    @Override
    public void clear() {
        eventos = new ArrayList<>();
    }

    @Override
    public void addEvento(Evento evento) {
        eventos.add(evento);
        log.info(evento.toString());
    }

    @Override
    public List<UUID> getUUIDsIndividuos() {
        return eventos.stream()
                .filter(event -> event instanceof IndividuoEvento)
                .map(event -> ((IndividuoEvento) event).getUuid())
                .distinct()
                .sorted(Comparator.comparing(UUID::toString))
                .collect(Collectors.toList());
    }

}
