package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class TesoroRecurso extends Recurso{
    public TesoroRecurso(Long generacion, RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.TESORO, generacion, recursoConfiguracion);
    }
}
