package net.gameoflife.servicios;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.objetos.recursos.Recurso;

public interface RecursoServicio {
    Recurso getRandomRecurso(long generacion, RecursoConfiguracion recursoConfiguracion);

    Recurso getRecurso(long generacion, TipoRecurso tipoRecurso, RecursoConfiguracion recursoConfiguracion);

    TipoRecurso getTipoRecursoDeComboLabel(String label);

    void aplicarRecursos(Casilla casilla);

    void crear(long generacion, Casilla casilla);

    void actualizarVidaRecursos(Casilla casilla);
}
