package net.gameoflife.objetos.recursos;

import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.point.Point2D;

@Getter
@Setter
public class Recurso {

    private final TipoRecurso tipoRecurso;
    private Point2D<Integer> posicion;
    private Integer vida;


    public Recurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }
}
