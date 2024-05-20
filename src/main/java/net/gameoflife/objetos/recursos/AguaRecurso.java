package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class AguaRecurso extends Recurso{
    public AguaRecurso(Long generacion, RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.AGUA, generacion, recursoConfiguracion);
    }
}
