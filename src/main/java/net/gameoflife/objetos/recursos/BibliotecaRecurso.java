package net.gameoflife.objetos.recursos;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class BibliotecaRecurso extends Recurso{
    public BibliotecaRecurso(RecursoConfiguracion recursoConfiguracion) {
        super(TipoRecurso.BIBLIOTECA,recursoConfiguracion);
    }
}
