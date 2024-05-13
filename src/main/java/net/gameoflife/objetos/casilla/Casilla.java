package net.gameoflife.objetos.casilla;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;

import java.util.List;

@Getter
@Setter
@Builder
public class Casilla {
    private Point2D<Integer> posicionTablero;
    private List<Individuo> individuos;
    private List<Recurso> recursos;
}
