package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class AguaRecurso extends Recurso{
    public AguaRecurso(RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.AGUA,recursoConfiguracion);
    }
}
