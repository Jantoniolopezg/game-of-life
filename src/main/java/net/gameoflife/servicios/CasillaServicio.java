package net.gameoflife.servicios;

import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.point.Point2D;

import java.util.Optional;

public interface CasillaServicio {

    Casilla getRandomCasilla();

    Casilla getCasillaVacia();

    Optional<Casilla> getCasillaDePosicionEnPantalla(Casilla[][] tablero, Point2D<Double> posicion, Point2D<Double> limites);

}
