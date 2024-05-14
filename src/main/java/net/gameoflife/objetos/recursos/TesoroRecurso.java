package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class TesoroRecurso extends Recurso{
    public TesoroRecurso(RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.TESORO,recursoConfiguracion);
    }
}
