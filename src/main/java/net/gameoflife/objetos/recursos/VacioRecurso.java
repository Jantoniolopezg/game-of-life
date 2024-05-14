package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class VacioRecurso extends Recurso {
    public VacioRecurso(RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.VACIO,recursoConfiguracion);
    }
}
