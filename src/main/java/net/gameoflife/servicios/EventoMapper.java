package net.gameoflife.servicios;

import net.gameoflife.enumeraciones.RazonMuerte;
import net.gameoflife.eventos.IndividuoApareceEvento;
import net.gameoflife.eventos.IndividuoMuereEvento;
import net.gameoflife.eventos.IndividuoReproduceEvento;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;

public interface EventoMapper {
    IndividuoApareceEvento mapIndividuoApareceEvento(long generation, Individuo newIndividuo, Casilla casilla);
    IndividuoReproduceEvento mapIndividuoReproduceEvento(long generation, Individuo individuo, Individuo pareja, Individuo hijo, Casilla casilla);
    IndividuoMuereEvento mapIndividuoMuereEvento(long generation, Individuo individuo, Casilla casilla, RazonMuerte razonMuerte);
}
