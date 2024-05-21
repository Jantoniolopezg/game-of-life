package net.gameoflife.servicios.impl;

import lombok.RequiredArgsConstructor;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.CasillaServicio;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.servicios.IndividuoServicio;
import net.gameoflife.servicios.RecursoServicio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.gameoflife.Constantes.Constantes.CELL_HEIGHT;
import static net.gameoflife.Constantes.Constantes.CELL_WIDTH;

@Service
@RequiredArgsConstructor
public class CasillaServicioImpl implements CasillaServicio {
    private final RecursoServicio recursoServicio;
    private final IndividuoServicio individuoServicio;
    private final ConfiguracionServicio configuracionServicio;

    @Override
    public Casilla getRandomCasilla() {
        final Configuracion configuracion = configuracionServicio.getConfiguracion();
        final JuegoConfiguracion juegoConfiguracion = configuracion.getJuegoConfiguracion();

        // Generar los recursos y guardar solo aquellos que no son de tipo EMPTY.
        final List<Recurso> recursos = new ArrayList<>();
        for (int i = 0; i < juegoConfiguracion.getMaxRecursosPorCasilla(); ++i) {
            recursos.add(recursoServicio.getRandomRecurso(0L, configuracion.getRecursoConfiguracion()));
        }
        final List<Recurso> recursosFiltrados = recursos.stream()
                .filter(recurso -> recurso.getTipoRecurso() != TipoRecurso.VACIO)
                .toList();

        // Generar los individuos y guardar solo aquellos que no son de tipo NONE.
        final List<Individuo> individuos = new ArrayList<>();
        for (int i = 0; i < juegoConfiguracion.getMaxIndividuosPorCasilla(); ++i) {
            individuos.add(individuoServicio.getRandomIndividuo(0L, configuracion.getIndividuoConfiguracion()));
        }
        final List<Individuo> individuosFiltrados = individuos.stream()
                .filter(individuo -> individuo.getTipoIndividuo() != TipoIndividuo.NINGUNO)
                .toList();

        return Casilla.builder()
                .recursos(recursosFiltrados)
                .individuos(individuosFiltrados)
                .build();
    }

    @Override
    public Casilla getCasillaVacia() {
        return Casilla.builder()
                .recursos(new ArrayList<>())
                .individuos(new ArrayList<>())
                .build();
    }

    /**
     * Recupera la celda correspondiente en el tablero a partir del tablero, unas coordenadas absolutas (en píxels)
     * y los límites del tablero de juego (en píxels).
     */
    @Override
    public Optional<Casilla> getCasillaDePosicionEnPantalla(Casilla[][] tablero, Point2D<Double> posicion, Point2D<Double> limites) {
        if ((posicion.getX() >= 0 && posicion.getX() <= limites.getX() -1) && (posicion.getY() >= 0 && posicion.getY() <= limites.getY() - 1)) {
            final int roundedX = (int) Math.floor(posicion.getX() / CELL_WIDTH);
            final int roundedY = (int) Math.floor(posicion.getY() / CELL_HEIGHT);
            return Optional.of(tablero[roundedX][roundedY]);
        }else{
            return Optional.empty();
        }
    }

}
