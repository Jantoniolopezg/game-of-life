package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class ComidaRecurso extends Recurso{
    public ComidaRecurso(Long generacion, RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.COMIDA, generacion, recursoConfiguracion);
    }
}
