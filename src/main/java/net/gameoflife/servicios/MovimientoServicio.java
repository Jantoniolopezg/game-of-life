package net.gameoflife.servicios;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.recursos.ExtendedRecurso;
import net.gameoflife.point.Point2D;

import java.util.List;

public interface MovimientoServicio {

    void move(ParametrosMovimiento parametrosMovimiento);

    void moveBasicoIndividuo(ParametrosMovimiento parametrosMovimiento, Individuo individuo);

    void moveNormalYAvanzadoIndividuo(ParametrosMovimiento parametrosMovimiento, Individuo individuo);

    List<ExtendedRecurso> getRecursosYDistancia(Casilla[][] tablero, Point2D<Double> source, Point2D<Double> target, TipoIndividuo tipoIndividuo);

    Point2D<Double> getRecursoPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo);

    void actualizarIndividuoDireccionYObjetivoPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo);
}
