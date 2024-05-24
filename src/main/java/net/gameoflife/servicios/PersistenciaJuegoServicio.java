package net.gameoflife.servicios;

import net.gameoflife.objetos.juego.DatosJuego;

public interface PersistenciaJuegoServicio {
    DatosJuego load();

    void save(DatosJuego datosJuego);
}
