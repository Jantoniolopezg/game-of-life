package net.gameoflife.servicios;

import net.gameoflife.eventos.Evento;

import java.util.List;
import java.util.UUID;

public interface EventoServicio {

    void clear();

    void addEvento(Evento evento);

    void setEventos(List<Evento> eventos);

    List<Evento> getEventos();

    List<UUID> getUUIDsIndividuos();
}
