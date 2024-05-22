package net.gameoflife.servicios.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.enumeraciones.TipoTablero;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.fxml.UserData;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.juego.CondicionesFinalizado;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.gameoflife.Constantes.Constantes.INDIVIDUAL;
import static net.gameoflife.Constantes.Constantes.RESOURCE;

@Slf4j
@Service
@RequiredArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimulacionServicioImpl implements SimulacionServicio {
    private final CasillaServicio casillaServicio;
    private final RecursoServicio recursoServicio;
    private final MovimientoServicio movimientoServicio;
    private final IndividuoServicio individuoServicio;
    private final ConfiguracionServicio configuracionServicio;
    @Getter
    @Setter
    private Casilla[][] tablero;
    @Getter
    private UserData userData;
    @Override
    public void initGame(UserData userData) {
        this.userData = userData;
        initTablero();
    }

    @Override
    public void initTablero() {
        final Point2D<Integer> tableroSize = configuracionServicio.getConfiguracion().getJuegoConfiguracion().getSize();
        tablero = new Casilla[tableroSize.getX()][tableroSize.getY()];
        for (int y = 0; y < tableroSize.getY(); ++y) {
            for (int x = 0; x < tableroSize.getX(); ++x) {
                final Casilla randomCasilla = TipoTablero.RANDOM.equals(userData.getTipoTablero()) ? casillaServicio.getRandomCasilla() :casillaServicio.getCasillaVacia();
                randomCasilla.setBoardPosition(new Point2D<>(x,y));
                tablero[x][y] = randomCasilla;
            }
        }
    }

    @Override
    public void addElemento(Long generation, Casilla casilla, String itemSeleccionado) {
        if (StringUtils.startsWith(itemSeleccionado, INDIVIDUAL)) {
            addIndividuoElemento(generation, casilla, itemSeleccionado);
        } else if (StringUtils.startsWith(itemSeleccionado, RESOURCE)) {
            addRecursoElemento(generation, casilla, itemSeleccionado);
        }
    }

    @Override
    public void removeElemento(Long value, Casilla casilla, String itemSeleccionado) {
        if (StringUtils.startsWith(itemSeleccionado, INDIVIDUAL)) {
            removeIndividuoElement(casilla, itemSeleccionado);
        } else if (StringUtils.startsWith(itemSeleccionado, RESOURCE)) {
            removeRecursoElemento(casilla, itemSeleccionado);
        }
    }

    @Override
    public void runGameLoop(long generation) {
        final JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();
        final Point2D<Integer> tableroSize = juegoConfiguracion.getSize();

        // Para cada individuo, se actualiza su tiempo de vida, y en su caso se elimina si ha muerto
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                individuoServicio.actualizarVidaIndividuos(casilla);
            }
        }

        // Para cada recurso, evaluará si sigue activo o debe eliminarse (por su tiempo de aparición).
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                recursoServicio.actualizarVidaRecursos(casilla);
            }
        }

        // Se ejecutará el movimiento de cada individuo (siempre obligatorio).
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                final ParametrosMovimiento parametrosMovimiento = ParametrosMovimiento.builder()
                        .generation(generation)
                        .casilla(casilla)
                        .tablero(tablero)
                        .tableroSize(tableroSize)
                        .posicionIndividuo(new Point2D<>(x,y))
                        .build();
                movimientoServicio.move(parametrosMovimiento);
            }
        }

        // Para cada individuo evaluará las mejoras obtenidas por los distintos recursos que se
        // encuentren en su nueva posición.
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                recursoServicio.aplicarRecursos(casilla);
            }
        }

        // Para cada posición, evaluará si existe reproducción o no.
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                individuoServicio.reproducir(generation, casilla);
            }
        }

        // Para cada posición, evaluará si existe clonación o no.
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                individuoServicio.clonacion(generation, casilla);
            }
        }

        // Para cada posición del tablero en la que existan varios individuos, se evaluará
        // si deben desaparecer algunos.
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                individuoServicio.senicidio(casilla);
            }
        }

        // Para cada posición del tablero, se evaluará si deben aparecer nuevos recursos.
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                recursoServicio.crear(generation, casilla);
            }
        }

    }

    @Override
    public CondicionesFinalizado getCondicionesFinalizado(long generation){
        final List<Individuo> individuosPermanecientes = getIndividuosPermanecientes();
        if (generation > 0 && individuosPermanecientes.size() == 1){
            return CondicionesFinalizado.builder()
                    .individuo(individuosPermanecientes.get(0))
                    .terminado(true)
                    .todosMuertos(false)
                    .build();
        } else if (generation > 0 && individuosPermanecientes.size() == 0) {
            return CondicionesFinalizado.builder()
                    .individuo(null)
                    .terminado(true)
                    .todosMuertos(true)
                    .build();
        }
        return CondicionesFinalizado.builder()
                .individuo(null)
                .terminado(false)
                .todosMuertos(false)
                .build();
    }

    private List<Individuo> getIndividuosPermanecientes() {
        final Point2D<Integer> tableroSize = configuracionServicio.getConfiguracion().getJuegoConfiguracion().getSize();
        final List<Individuo> individuos = new ArrayList<>();
        for (double y = 0; y < tableroSize.getY(); y++) {
            for (double x = 0; x < tableroSize.getX(); x++) {
                final Casilla casilla = tablero[(int) x][(int) y];
                individuos.addAll(casilla.getIndividuos());
            }
        }
        return individuos;
    }

    private void addIndividuoElemento(Long generation, Casilla casilla, String itemSeleccionado){
        final TipoIndividuo tipoIndividuo = individuoServicio.getTipoIndividuoDeComboLabel(itemSeleccionado);
        final Individuo individuo = individuoServicio.getIndividuo(generation, tipoIndividuo, configuracionServicio.getConfiguracion().getIndividuoConfiguracion());
        final List<Individuo> individuos = casilla.getIndividuos();
        if (individuos.size() < configuracionServicio.getConfiguracion().getJuegoConfiguracion().getMaxIndividuosPorCasilla()) {
            final List<Individuo> nuevaListaIndividuos = new ArrayList<>(individuos);
            nuevaListaIndividuos.add(individuo);
            casilla.setIndividuos(nuevaListaIndividuos);
        }
    }

    private void addRecursoElemento(Long generation, Casilla casilla, String itemSeleccionado) {
        final TipoRecurso tipoRecurso = recursoServicio.getTipoRecursoDeComboLabel(itemSeleccionado);
        final Recurso recurso = recursoServicio.getRecurso(generation, tipoRecurso, configuracionServicio.getConfiguracion().getRecursoConfiguracion());
        final List<Recurso> recursos = casilla.getRecursos();
        if (recursos.size() < configuracionServicio.getConfiguracion().getJuegoConfiguracion().getMaxRecursosPorCasilla()) {
            final List<Recurso> nuevaListaRecursos = new ArrayList<>(recursos);
            nuevaListaRecursos.add(recurso);
            casilla.setRecursos(nuevaListaRecursos);
        }
    }

    private void removeIndividuoElement(Casilla casilla, String itemSeleccionado){
        final TipoIndividuo tipoIndividuo = individuoServicio.getTipoIndividuoDeComboLabel(itemSeleccionado);
        final List<Individuo> individuosFiltrados = casilla.getIndividuos().stream()
                .filter(individuo -> tipoIndividuo != individuo.getTipoIndividuo())
                .toList();
        casilla.setIndividuos(individuosFiltrados);
    }

    private void removeRecursoElemento(Casilla casilla, String itemSeleccionado) {
        final TipoRecurso tipoRecurso = recursoServicio.getTipoRecursoDeComboLabel(itemSeleccionado);
        final List<Recurso> recursosFiltrados = casilla.getRecursos().stream()
                .filter(recurso -> tipoRecurso != recurso.getTipoRecurso())
                .toList();
        casilla.setRecursos(recursosFiltrados);
    }
}
