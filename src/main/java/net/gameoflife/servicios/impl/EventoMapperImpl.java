package net.gameoflife.servicios.impl;

import net.gameoflife.enumeraciones.RazonMuerte;
import net.gameoflife.enumeraciones.TipoEvento;
import net.gameoflife.eventos.IndividuoApareceEvento;
import net.gameoflife.eventos.IndividuoMuereEvento;
import net.gameoflife.eventos.IndividuoReproduceEvento;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.servicios.EventoMapper;
import org.springframework.stereotype.Service;

@Service
public class EventoMapperImpl implements EventoMapper {
    @Override
    public IndividuoApareceEvento mapIndividuoApareceEvento(long generation, Individuo newIndividuo, Casilla casilla) {
        return IndividuoApareceEvento.builder()
                .generation(generation)
                .tipoEvento(TipoEvento.INDIVIDUO_APARECE)
                .uuid(newIndividuo.getUuid())
                .tipoIndividuo(newIndividuo.getTipoIndividuo())
                .posicion(casilla.getBoardPosition())
                .build();
    }

    @Override
    public IndividuoReproduceEvento mapIndividuoReproduceEvento(long generation, Individuo individuo, Individuo pareja, Individuo hijo, Casilla casilla) {
        return IndividuoReproduceEvento.builder()
                .generation(generation)
                .tipoEvento(TipoEvento.INDIVIDUO_REPRODECE)
                .uuid(individuo.getUuid())
                .tipoIndividuo(individuo.getTipoIndividuo())
                .parejaTipo(pareja.getTipoIndividuo())
                .parejaUUID(pareja.getUuid())
                .posicion(casilla.getBoardPosition())
                .hijoUUID(hijo.getUuid())
                .hijoTipo(hijo.getTipoIndividuo())
                .build();
    }

    @Override
    public IndividuoMuereEvento mapIndividuoMuereEvento(long generation, Individuo individuo, Casilla casilla, RazonMuerte razonMuerte) {
        return IndividuoMuereEvento.builder()
                .generation(generation)
                .tipoEvento(TipoEvento.INDIVIDUO_MUERE)
                .uuid(individuo.getUuid())
                .tipoIndividuo(individuo.getTipoIndividuo())
                .posicion(casilla.getBoardPosition())
                .razonMuerte(razonMuerte)
                .build();
    }

}
