package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class PozoRecurso extends Recurso{
    public PozoRecurso(Long generacion, RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.POZO, generacion, recursoConfiguracion);
    }
}
