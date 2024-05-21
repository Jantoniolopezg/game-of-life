package net.gameoflife.servicios.impl;

import lombok.RequiredArgsConstructor;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.recursos.ExtendedRecurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.MovimientoServicio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.gameoflife.utils.MathsUtil.clamp;

/**
 * Este servicio se encarga de la lógica para mover los individuos. Se ha optado por realizar un servicio aparte
 * en lugar de utilizar polimorfismo en la clase Individual y sus subclases por la complejidad de la lógica.
 * Muchos métodos de esta clase son públicos a propósito para que resulte más sencillo probarlos unitariamente.
 */
@Service
@RequiredArgsConstructor
public class MovimientoServicioImpl implements MovimientoServicio {
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
        addIndividuoACasillaEnNuevaPosicion(parametrosMovimiento, individuo);
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
            //metodo_que_cambia_la_direccion_y_el_target(parametrosMovimiento, individuo);
        }

        // Eliminar al individuo de la celda actual.
        eliminarIndividuoDeCasillaActual(parametrosMovimiento, individuo);

        // Añadir al individuo en la lista de individuos de la celda con la nueva posición.
        addIndividuoACasillaEnNuevaPosicion(parametrosMovimiento, individuo);
    }

    @Override
    public List<ExtendedRecurso> getRecursosYDistancia(Casilla[][] tablero, Point2D<Double> source, Point2D<Double> target, TipoIndividuo tipoIndividuo) {
        return null;
    }

    @Override
    public Point2D<Double> getRecursoPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        return null;
    }

    @Override
    public void actualizarIndividuoDireccionYObjetivoPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {

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
    private void addIndividuoACasillaEnNuevaPosicion(ParametrosMovimiento parametrosMovimiento, Individuo individuo) {
        final Point2D<Double> posicionActual = parametrosMovimiento.getPosicionIndividuo();
        final Casilla[][] tablero = parametrosMovimiento.getTablero();
        final Double nuevoX = clamp(posicionActual.getX() + individuo.getDireccion().getX(), 0.0, parametrosMovimiento.getTableroSize().getX() - 1.0);
        final Double nuevoY = clamp(posicionActual.getY() + individuo.getDireccion().getY(), 0.0, parametrosMovimiento.getTableroSize().getY () - 1.0);
        final Casilla casillaDestino = tablero[nuevoX.intValue()][nuevoY.intValue()];
        final List<Individuo> caillaDestinoIndividuos = new ArrayList<>(casillaDestino.getIndividuos());
        caillaDestinoIndividuos.add(individuo);
        casillaDestino.setIndividuos(caillaDestinoIndividuos);
    }

}
