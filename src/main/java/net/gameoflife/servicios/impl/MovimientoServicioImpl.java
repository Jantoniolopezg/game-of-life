package net.gameoflife.servicios.impl;

import lombok.RequiredArgsConstructor;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.recursos.ExtendedRecurso;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.EventoMapper;
import net.gameoflife.servicios.EventoServicio;
import net.gameoflife.servicios.MovimientoServicio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static net.gameoflife.utils.MathsUtil.*;

/**
 * Este servicio se encarga de la lógica para mover los individuos. Se ha optado por realizar un servicio aparte
 * en lugar de utilizar polimorfismo en la clase Individual y sus subclases por la complejidad de la lógica.
 * Muchos métodos de esta clase son públicos a propósito para que resulte más sencillo probarlos unitariamente.
 */
@Service
@RequiredArgsConstructor
public class MovimientoServicioImpl implements MovimientoServicio {

    private final EventoMapper eventoMapper;
    private final EventoServicio eventoServicio;

    @Override
    public void move(ParametrosMovimiento parametrosMovimiento) {
        parametrosMovimiento.getCasilla().getIndividuos().forEach(individuo -> {
            switch (individuo.getTipoIndividuo()) {
                case BASICO -> moveBasicoIndividuo(parametrosMovimiento, individuo);
                case NORMAL, AVANZADO -> moveNormalYAvanzadoIndividuo(parametrosMovimiento, individuo);
            }
        });
    }

    /**
     * Movimiento aleatorio, una celda alrededor en cualquier dirección.
     */
    @Override
    public void moveBasicoIndividuo(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        // Calcular una dirección de movimiento.
        individuo.setDireccion(getPosicionRandomCercaDelIndividuo());

        // Eliminar al individuo de la celda actual.
        eliminarIndividuoDeCasillaActual(parametrosMovimiento, individuo);

        // Añadir al individuo en la lista de individuos de la celda con la nueva posición.
        final Casilla casillaNuevaPosicion = addIndividuoACasillaEnNuevaPosicion(parametrosMovimiento, individuo);
        eventoServicio.addEvento(eventoMapper.mapIndividuoMueveEvento(parametrosMovimiento, individuo, casillaNuevaPosicion.getBoardPosition()));
    }

    /**
     * Movimiento en línea recta. Para ello vamos a elegir una dirección inicial y cuando llegue al recurso seleccionado
     * del tablero seleccionaremos una nueva dirección.
     */
    @Override
    public void moveNormalYAvanzadoIndividuo(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        // Si el vector director es 0 o hemos llegado al objetivo, seleccionamos un objetivo nuevo.
        // También debemos hacerlo si el vector director hace que el individuo se mueva fuera del tablero.
        final Point2D<Double> direction = individuo.getDireccion();
        if ((direction.getX() == 0 && direction.getY() == 0) || (parametrosMovimiento.getPosicionIndividuo().getX() == individuo.getDestinoPosicion().getX().intValue() && parametrosMovimiento.getPosicionIndividuo().getY() == individuo.getDestinoPosicion().getY().intValue())) {
            actualizarIndividuoDireccionYObjetivoPosicion(parametrosMovimiento, individuo);
        }

        // Eliminar al individuo de la celda actual.
        eliminarIndividuoDeCasillaActual(parametrosMovimiento, individuo);

        // Añadir al individuo en la lista de individuos de la celda con la nueva posición.
        final Casilla casillaNuevaPosicion = addIndividuoACasillaEnNuevaPosicion(parametrosMovimiento, individuo);
        eventoServicio.addEvento(eventoMapper.mapIndividuoMueveEvento(parametrosMovimiento, individuo, casillaNuevaPosicion.getBoardPosition()));
    }

    /**
     * Devuelve una lista de ExtendedResources que contienen la distancia a los recursos, su posición y el recurso en sí.
     * Utiliza el individualType para hacer un filtrado adicional, los individuos de tipo NORMAL no evalúan si hay un
     * pozo entre ellos y el recurso al que quieren llegar, mientras que los ADVANCED si lo hacen.
     */
    @Override
    public List<ExtendedRecurso> getRecursosYDistancia(Casilla[][] tablero, Point2D<Double> source, Point2D<Double> target, TipoIndividuo tipoIndividuo) {
        final Casilla casilla = tablero[target.getX().intValue()][target.getY().intValue()];
        return casilla.getRecursos().stream()
                .filter(recurso -> recurso.getTipoRecurso() != TipoRecurso.POZO && recurso.getTipoRecurso() != TipoRecurso.MONTE)
                .map(recurso -> ExtendedRecurso.builder()
                        .recurso(recurso)
                        .distance(distance(source,target))
                        .position(new Point2D<>(target.getX(),target.getY()))
                        .build())
                .filter(resource -> (TipoIndividuo.AVANZADO.equals(tipoIndividuo))
                        ? avanzadoMovementPredicate(resource, tablero, source, target)
                        : normalMovementPredicate(resource))
                .toList();
    }

    private boolean normalMovementPredicate(ExtendedRecurso extendedRecurso) {
        return extendedRecurso.getDistance() != 0.0;
    }

    private boolean avanzadoMovementPredicate(ExtendedRecurso extendedRecurso, Casilla[][] tablero, Point2D<Double> source, Point2D<Double> target){
        return extendedRecurso.getDistance() != 0.0 && noPozoEntreOrigenYDestino(tablero, source, target);
    }

    private boolean noPozoEntreOrigenYDestino(Casilla[][] tablero, Point2D<Double> source, Point2D<Double> target){
        final double distance = Math.abs(distance(source,target));
        final Point2D<Double> direccionBusquedaNormalizada = normalize(new Point2D<>(target.getX() - source.getX(), target.getY() - source.getY()));
        for (double i = 0; i < distance; ++i) {
            final Point2D<Double> puntoBusqueda = new Point2D<>(source.getX() + direccionBusquedaNormalizada.getX(), source.getY() + direccionBusquedaNormalizada.getY());
            final Casilla casilla = tablero[puntoBusqueda.getX().intValue()][puntoBusqueda.getY().intValue()];
            final List<Recurso> recursos = casilla.getRecursos();
            boolean hasPozo = recursos.stream().anyMatch(recurso -> TipoRecurso.POZO.equals(recurso.getTipoRecurso()));
            if (hasPozo){
                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve la posición del recurso más cercano al jugador excluyendo los recursos perjudiciales
     * (MOUNTAIN y DWELL) y todos los que se encuentran en la misma celda.
     */
    @Override
    public Point2D<Double> getRecursoPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        final Point2D<Double> individuoPosicion = parametrosMovimiento.getPosicionIndividuo();
        final List<ExtendedRecurso> recursoCandidatos = new ArrayList<>();

        // Buscar recursos valiosos hacia arriba:
        for (double i = individuoPosicion.getY() - 1; i > -1; --i) {
            final Point2D<Double> target = new Point2D<>(parametrosMovimiento.getPosicionIndividuo().getX(), i);
            List<ExtendedRecurso> upRecursosYDistancia = getRecursosYDistancia(parametrosMovimiento.getTablero(), parametrosMovimiento.getPosicionIndividuo(), target, individuo.getTipoIndividuo());
            recursoCandidatos.addAll(upRecursosYDistancia);
        }

        // Buscar recursos valiosos hacia abajo:
        for (double i = individuoPosicion.getY() + 1; i < parametrosMovimiento.getTableroSize().getY(); ++i) {
            final Point2D<Double> target = new Point2D<>(parametrosMovimiento.getPosicionIndividuo().getX(), i);
            List<ExtendedRecurso> downRecursosYDistancia = getRecursosYDistancia(parametrosMovimiento.getTablero(), parametrosMovimiento.getPosicionIndividuo(), target, individuo.getTipoIndividuo());
            recursoCandidatos.addAll(downRecursosYDistancia);
        }

        // Buscar recursos valiosos hacia la izquierda:
        for (double i = individuoPosicion.getX() - 1; i > -1; --i) {
            final Point2D<Double> target = new Point2D<>(i, parametrosMovimiento.getPosicionIndividuo().getY());
            List<ExtendedRecurso> leftRecursosYDistancia = getRecursosYDistancia(parametrosMovimiento.getTablero(), parametrosMovimiento.getPosicionIndividuo(), target, individuo.getTipoIndividuo());
            recursoCandidatos.addAll(leftRecursosYDistancia);
        }

        // Buscar recursos valiosos hacia la derecha:
        for (double i = individuoPosicion.getX() + 1; i < parametrosMovimiento.getTableroSize().getX(); ++i) {
            final Point2D<Double> target = new Point2D<>(i, parametrosMovimiento.getPosicionIndividuo().getY());
            List<ExtendedRecurso> rightRecursosYDistancia = getRecursosYDistancia(parametrosMovimiento.getTablero(), parametrosMovimiento.getPosicionIndividuo(), target, individuo.getTipoIndividuo());
            recursoCandidatos.addAll(rightRecursosYDistancia);
        }

        // Desordenar la lista de resourceCandidates.
        Collections.shuffle(recursoCandidatos);

        // Ordenar los recursos por cercanía al individuo (ya se han eliminado aquellos que están en la misma celda en el
        // método getResourcesAndDistance, puesto que todos los individuos deben moverse en cada turno).
        // Para los individuos NORMAL devuelve un recurso al azar entre los disponibles, para los individuos ADVANCED,
        // devuelve la posición del más cercano siempre y cuando no haya un pozo entre su posición y la del recurso, en
        // cualquier otro caso el individuo no se moverá.

        return recursoCandidatos.stream().findFirst()
                .map(ExtendedRecurso::getPosition)
                .orElseGet(() -> new Point2D<>(0.0, 0.0));
    }

    /**
     * TargetPosition es la posición del recurso al que queremos llegar.
     * Direction es el vector director hacia esa posición normalizada (puesto que los individuos NORMAL o ADVANCED no
     * se mueven en diagonal, siempre van hacia arriba o abajo o hacia la derecha o izquierda el vector director
     * normalizado siempre es arriba: (0, 1) o abajo: (0, -1) o izquierda: (-1, 0) o derecha: (0, 1)).
     * <p>
     * Puede darse el caso de que target position sea (0,0) por ejemplo, un ADVANCED rodeado de pozos, en ese
     * caso el individuo no se moverá, en tal caso el vector director es (0, 0).
     */
    @Override
    public void actualizarIndividuoDireccionYObjetivoPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        final Point2D<Double> objetivoPosicion = getRecursoPosicion(parametrosMovimiento, individuo);
        if (objetivoPosicion.getX() != 0.0 && objetivoPosicion.getY() != 0.0){
            final Point2D<Double> actualPosicion = new Point2D<>(parametrosMovimiento.getPosicionIndividuo().getX(), parametrosMovimiento.getPosicionIndividuo().getY());
            final Point2D<Double> direccionVector = new Point2D<>(objetivoPosicion.getX() - actualPosicion.getX(), objetivoPosicion.getY() - actualPosicion.getY());
            final Point2D<Double> normalizadaDireccion = normalize(direccionVector);
            individuo.setDireccion(normalizadaDireccion);
            individuo.setDestinoPosicion(objetivoPosicion);
        }else {
            individuo.setDireccion(new Point2D<>(0.0, 0.0));
            individuo.setDestinoPosicion(new Point2D<>(0.0, 0.0));
        }
    }

    /**
     * Calcular una nueva posición al azar, en caso de que la posición resultante sea 0 se recalcula excluyendo 0 de
     * los resultados que puede devolver el random, puesto que los individuos deben moverse.
     */
    private Point2D<Double> getPosicionRandomCercaDelIndividuo() {
        double nuevaPosicionX = new Random().nextInt(3) - 1;
        double nuevaPosicionY = new Random().nextInt(3) - 1;
        if (nuevaPosicionX == 0 && nuevaPosicionY == 0){
            nuevaPosicionX = new Random().nextBoolean() ? 1 : -1;
            nuevaPosicionY = new Random().nextBoolean() ? 1 : -1;
        }
        return new Point2D<>(nuevaPosicionX,nuevaPosicionY);
    }

    /**
     * Eliminar al individuo de la celda actual
     */
    private void eliminarIndividuoDeCasillaActual(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        final Casilla casilla = parametrosMovimiento.getCasilla();
        final List<Individuo> individuos = new ArrayList<>(casilla.getIndividuos());
        individuos.remove(individuo);
        casilla.setIndividuos(individuos);
    }

    /**
     * Añadir al individuo en la lista de individuos de la celda con la nueva posición.
     */
    private Casilla addIndividuoACasillaEnNuevaPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        final Point2D<Double> posicionActual = parametrosMovimiento.getPosicionIndividuo();
        final Casilla[][] tablero = parametrosMovimiento.getTablero();
        final Double nuevoX = clamp(posicionActual.getX() + individuo.getDireccion().getX(), 0.0, parametrosMovimiento.getTableroSize().getX() - 1.0);
        final Double nuevoY = clamp(posicionActual.getY() + individuo.getDireccion().getY(), 0.0, parametrosMovimiento.getTableroSize().getY () - 1.0);
        final Casilla casillaDestino = tablero[nuevoX.intValue()][nuevoY.intValue()];
        final List<Individuo> caillaDestinoIndividuos = new ArrayList<>(casillaDestino.getIndividuos());
        caillaDestinoIndividuos.add(individuo);
        casillaDestino.setIndividuos(caillaDestinoIndividuos);
        return casillaDestino;
    }

}
