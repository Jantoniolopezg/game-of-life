package net.gameoflife.servicios;

import net.gameoflife.objetos.casilla.Casilla;

public interface PersistenciaJuegoServicio {
    Casilla[][] load();

    void save(Casilla[][] tablero);
}
