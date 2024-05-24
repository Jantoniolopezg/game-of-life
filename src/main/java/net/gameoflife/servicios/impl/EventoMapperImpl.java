package net.gameoflife.servicios.impl;

import net.gameoflife.enumeraciones.RazonMuerte;
import net.gameoflife.enumeraciones.TipoEvento;
import net.gameoflife.eventos.*;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
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
                .tipoEvento(TipoEvento.INDIVIDUO_REPRODUCE)
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

    @Override
    public RecursoDesapareceEvento mapRecursoDesapareceEvento(long generation, Recurso recurso, Casilla casilla) {
        return RecursoDesapareceEvento.builder()
                .generation(generation)
                .tipoEvento(TipoEvento.RECURSO_DESAPARECE)
                .tipoRecurso(recurso.getTipoRecurso())
                .uuid(recurso.getUuid())
                .posicion(casilla.getBoardPosition())
                .build();
    }

    @Override
    public IndividuoMueveEvento mapIndividuoMueveEvento(ParametrosMovimiento parametrosMovimiento, Individuo individuo, Point2D<Integer> nextPosition) {
        final Point2D<Double> individuoPosition = parametrosMovimiento.getPosicionIndividuo();
        final Point2D<Integer> position = new Point2D<>(individuoPosition.getX().intValue(), individuoPosition.getY().intValue());
        return IndividuoMueveEvento.builder()
                .uuid(individuo.getUuid())
                .generation(parametrosMovimiento.getGeneration())
                .tipoEvento(TipoEvento.INDIVIDUO_MUEVE)
                .tipoIndividuo(individuo.getTipoIndividuo())
                .posicion(position)
                .newPosicion(nextPosition)
                .direccion(individuo.getDireccion())
                .build();
    }
}
