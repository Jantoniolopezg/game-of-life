package net.gameoflife.objetos.individuos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.point.Point2D;

@Getter
@Setter
@Builder
public class ParametrosMovimiento {
    private Long generation;
    private Casilla casilla;
    private Casilla[][] tablero;
    private Point2D<Integer> tableroSize;
    private Point2D<Double> posicionIndividuo;
}
