package net.gameoflife.servicios.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.objetos.recursos.*;
import net.gameoflife.servicios.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.gameoflife.Constantes.Constantes.RESOURCE;
import static net.gameoflife.utils.MathsUtil.clamp;
import static net.gameoflife.utils.MathsUtil.isInRange;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecursoServicioImpl implements RecursoServicio {
    private static final double TOTAL_UPPER_LIMIT = 6.0;

    private final EventoMapper eventoMapper;
    private final EventoServicio eventoServicio;
    private final IndividuoServicio individuoServicio;
    private final ConfiguracionServicio configuracionServicio;

    @Override
    public Recurso getRandomRecurso(long generacion, RecursoConfiguracion recursoConfiguracion) {
        final Random random = new Random();
        final double numeroSeleccionado = random.doubles(0.0,TOTAL_UPPER_LIMIT).findFirst().getAsDouble();
        final double waterLowerLimit = 0.0;
        final double waterUpperLimit = recursoConfiguracion.getAguaProbabilidadRecurso().doubleValue();
        final double foodLowerLimit = waterUpperLimit + 0.001;
        final double foodUpperLimit = waterUpperLimit + recursoConfiguracion.getComidaProbabilidadRecurso().doubleValue();
        final double mountainLowerLimit = foodUpperLimit + 0.001;
        final double mountainUpperLimit = foodUpperLimit + recursoConfiguracion.getMonteProbabilidadRecurso().doubleValue();
        final double treasureLowerLimit = mountainUpperLimit + 0.001;
        final double treasureUpperLimit = mountainUpperLimit + recursoConfiguracion.getTesoroProbabilidadRecurso().doubleValue();
        final double libraryLowerLimit = treasureUpperLimit + 0.001;
        final double libraryUpperLimit = treasureUpperLimit + recursoConfiguracion.getBibliotecaProbabilidadRecurso().doubleValue();
        final double dwellLowerLimit = libraryUpperLimit + 0.001;
        final double dwellUpperLimit = libraryUpperLimit + recursoConfiguracion.getPozoProbabilidadRecurso().doubleValue();

        if (isInRange(waterLowerLimit, waterUpperLimit, numeroSeleccionado)) {
            return new AguaRecurso(generacion, recursoConfiguracion);
        } else if (isInRange(foodLowerLimit, foodUpperLimit, numeroSeleccionado)) {
            return new ComidaRecurso(generacion, recursoConfiguracion);
        } else if (isInRange(mountainLowerLimit, mountainUpperLimit, numeroSeleccionado)) {
            return new MonteRecurso(generacion, recursoConfiguracion);
        } else if (isInRange(treasureLowerLimit, treasureUpperLimit, numeroSeleccionado)) {
            return new TesoroRecurso(generacion, recursoConfiguracion);
        } else if (isInRange(libraryLowerLimit, libraryUpperLimit, numeroSeleccionado)) {
            return new BibliotecaRecurso(generacion, recursoConfiguracion);
        } else if (isInRange(dwellLowerLimit, dwellUpperLimit, numeroSeleccionado)) {
            return new PozoRecurso(generacion, recursoConfiguracion);
        }
        return new VacioRecurso(generacion,recursoConfiguracion);
    }

    @Override
    public Recurso getRecurso(long generacion, TipoRecurso tipoRecurso, RecursoConfiguracion recursoConfiguracion) {
        Recurso recurso = new AguaRecurso(generacion,recursoConfiguracion);
        switch (tipoRecurso) {
            case COMIDA -> recurso = new ComidaRecurso(generacion,recursoConfiguracion);
            case MONTE -> recurso = new MonteRecurso(generacion,recursoConfiguracion);
            case TESORO -> recurso = new TesoroRecurso(generacion,recursoConfiguracion);
            case BIBLIOTECA -> recurso = new BibliotecaRecurso(generacion,recursoConfiguracion);
            case POZO -> recurso = new PozoRecurso(generacion,recursoConfiguracion);
        }
        return recurso;
    }

    @Override
    public TipoRecurso getTipoRecursoDeComboLabel(String label) {
        final String nombreItem = StringUtils.remove(label,RESOURCE);
        return TipoRecurso.valueOf(nombreItem);
    }

    @Override
    public void aplicarRecursos(Casilla casilla) {
        final List<Recurso> recursos = casilla.getRecursos();
        recursos.forEach(recurso -> {
            switch (recurso.getTipoRecurso()) {
                case AGUA -> aplicarAgua(casilla);
                case COMIDA -> aplicarComida(casilla);
                case MONTE -> aplicarMonte(casilla);
                case TESORO -> aplicarTesoro(casilla);
                case BIBLIOTECA -> aplicarBiblioteca(casilla);
                case POZO -> aplicarPozo(casilla);
            }
        });
    }

    private void aplicarAgua(Casilla casilla){
        casilla.getIndividuos().forEach(individuo -> {
            individuo.setVida(clamp(individuo.getVida() + 2, 0, 10));
        });
    }

    private void aplicarComida(Casilla casilla){
        casilla.getIndividuos().forEach(individuo -> {
            individuo.setVida(clamp(individuo.getVida() + 10, 0, 10));
        });
    }

    private void aplicarMonte(Casilla casilla){
        casilla.getIndividuos().forEach(individuo -> {
            individuo.setVida(clamp(individuo.getVida() - 2, 0, 10));
        });
    }

    private void aplicarTesoro(Casilla casilla){
        casilla.getIndividuos().forEach(individuo -> {
            final BigDecimal factorMejoraReproduccionTesoro = configuracionServicio.getConfiguracion().getRecursoConfiguracion().getTesoroFactorMejoraReproduccion();
            final BigDecimal probabilidadReproduccionAumentada = individuo.getProbabilidadReproduccion().multiply(BigDecimal.ONE.add(factorMejoraReproduccionTesoro));
            individuo.setProbabilidadReproduccion(BigDecimal.valueOf(clamp(probabilidadReproduccionAumentada.doubleValue(), 0.0, 1.0)));
        });
    }

    private void aplicarBiblioteca(Casilla casilla){
        casilla.getIndividuos().forEach(individuo -> {
            final BigDecimal factorMejoraClonacionBiblioteca = configuracionServicio.getConfiguracion().getRecursoConfiguracion().getBibliotecaFactorMejoraClonacion();
            final BigDecimal probabilidadClonacionAumentada = individuo.getProbabilidadClonacion().multiply(BigDecimal.ONE.add(factorMejoraClonacionBiblioteca));
            individuo.setProbabilidadClonacion(BigDecimal.valueOf(clamp(probabilidadClonacionAumentada.doubleValue(), 0.0, 1.0)));
        });
    }

    private void aplicarPozo(Casilla casilla){
        casilla.getIndividuos().forEach(individuo -> {
            log.info("Individuo eliminado por pozo: " + individuo);
            individuoServicio.eliminarIndividuo(casilla, individuo);
        });
    }

    @Override
    public void crear(long generacion, Casilla casilla) {
        final List<Recurso> recursos = new ArrayList<>(casilla.getRecursos());
        final Configuracion configuracion = configuracionServicio.getConfiguracion();
        final JuegoConfiguracion juegoConfiguracion = configuracion.getJuegoConfiguracion();
        final RecursoConfiguracion recursoConfiguracion = configuracion.getRecursoConfiguracion();
        if (recursos.size() < juegoConfiguracion.getMaxRecursosPorCasilla()) {
            final Recurso recursoRandom = getRandomRecurso(generacion,recursoConfiguracion);
            if (recursoRandom.getTipoRecurso() != TipoRecurso.VACIO) {
                recursos.add(recursoRandom);
                casilla.setRecursos(recursos);
            }
        }
    }

    @Override
    public void actualizarVidaRecursos(long generacion, Casilla casilla) {
        casilla.getRecursos().forEach(recurso -> {
            boolean isActivo = actualizaVidaRecurso(-1,recurso);
            if (!isActivo){
                eliminarRecurso(casilla, recurso);
                eventoServicio.addEvento(eventoMapper.mapRecursoDesapareceEvento(generacion, recurso, casilla));
            }
        });
    }

    /**
     * Devuelve true si el recurso está activo.
     */
    private boolean actualizaVidaRecurso(int amount, Recurso recurso){
        final int vidaActualizada = recurso.getVidaRecurso() + amount;
        recurso.setVidaRecurso(clamp(vidaActualizada, 0, 10));
        return vidaActualizada > 0;
    }

    private void eliminarRecurso(Casilla casilla, Recurso recurso) {
        final List<Recurso> recursos = casilla.getRecursos().stream()
                .filter(res -> !res.getUuid().equals(recurso.getUuid()))
                .toList();
        casilla.setRecursos(recursos);
    }

}
