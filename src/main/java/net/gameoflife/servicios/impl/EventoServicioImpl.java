package net.gameoflife.servicios.impl;

import lombok.extern.slf4j.Slf4j;
import net.gameoflife.eventos.Evento;
import net.gameoflife.servicios.EventoServicio;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EventoServicioImpl implements EventoServicio {

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

}
