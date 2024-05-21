package net.gameoflife.objetos.recursos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.point.Point2D;

@Getter
@Setter
@Builder
public class ExtendedRecurso {
    private Recurso recurso;
    private Point2D<Double> position;
    private double distance;
}
