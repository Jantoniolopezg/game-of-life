package net.gameoflife.servicios;

import net.gameoflife.enumeraciones.RazonMuerte;
import net.gameoflife.eventos.*;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;

public interface EventoMapper {
    IndividuoApareceEvento mapIndividuoApareceEvento(long generation, Individuo newIndividuo, Casilla casilla);

    IndividuoReproduceEvento mapIndividuoReproduceEvento(long generation, Individuo individuo, Individuo pareja, Individuo hijo, Casilla casilla);

    IndividuoMuereEvento mapIndividuoMuereEvento(long generation, Individuo individuo, Casilla casilla, RazonMuerte razonMuerte);

    RecursoDesapareceEvento mapRecursoDesapareceEvento(long generation, Recurso recurso, Casilla casilla);

    IndividuoMueveEvento mapIndividuoMueveEvento(ParametrosMovimiento parametrosMovimiento, Individuo individuo, Point2D<Integer> newPosicion);
}
