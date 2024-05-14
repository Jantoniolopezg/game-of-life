package net.gameoflife.objetos.recursos;

import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.point.Point2D;

@Getter
@Setter
public class Recurso {

    private final TipoRecurso tipoRecurso;
    private final RecursoConfiguracion recursoConfiguracion;
    private Point2D<Integer> posicion;
    private Integer vida;


    public Recurso(TipoRecurso tipoRecurso, RecursoConfiguracion recursoConfiguracion) {
        this.tipoRecurso = tipoRecurso;
        this.recursoConfiguracion = recursoConfiguracion;
    }

    @Override
    public String toString(){
        return "Recurso{" +
                "tipoRecurso=" + tipoRecurso +
                ", posicion=" + posicion +
                ", vida=" + vida +
                "}";
    }
}
