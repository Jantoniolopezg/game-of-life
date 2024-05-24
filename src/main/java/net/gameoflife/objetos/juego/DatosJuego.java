package net.gameoflife.objetos.juego;

import lombok.Builder;
import lombok.Getter;
import net.gameoflife.eventos.Evento;
import net.gameoflife.objetos.casilla.Casilla;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class DatosJuego implements Serializable {
    Casilla[][] tablero;
    List<Evento> eventos;
}

