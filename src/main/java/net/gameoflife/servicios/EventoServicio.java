package net.gameoflife.servicios;

import net.gameoflife.eventos.Evento;

public interface EventoServicio {

    void clear();

    void addEvento(Evento evento);
}
