package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class MonteRecurso extends Recurso{
    public MonteRecurso(RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.MONTE,recursoConfiguracion);
    }
}
