package net.gameoflife.servicios.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.enumeraciones.RazonMuerte;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.individuos.AvanzadoIndividuo;
import net.gameoflife.objetos.individuos.BasicoIndividuo;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.NormalIndividuo;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.servicios.EventoMapper;
import net.gameoflife.servicios.EventoServicio;
import net.gameoflife.servicios.IndividuoServicio;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static net.gameoflife.Constantes.Constantes.INDIVIDUAL;
import static net.gameoflife.utils.MathsUtil.clamp;
import static net.gameoflife.utils.MathsUtil.isInRange;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividuoServicioImpl implements IndividuoServicio {

    private final EventoMapper eventoMapper;
    private final EventoServicio eventoServicio;
    private final ConfiguracionServicio configuracionServicio;
    @Override
    public Individuo getRandomIndividuo(Long generacion, IndividuoConfiguracion individuoConfiguracion) {
        final TipoIndividuo tipoIndividuo = TipoIndividuo.values()[new Random().nextInt(TipoIndividuo.values().length)];
        return getIndividuo(generacion, tipoIndividuo, individuoConfiguracion);
    }

    @Override
    public Individuo getIndividuo(Long generacion, TipoIndividuo tipoIndividuo, IndividuoConfiguracion individuoConfiguracion) {
        Individuo individuo = new BasicoIndividuo(generacion,individuoConfiguracion);
        switch (tipoIndividuo) {
            case NORMAL -> individuo = new NormalIndividuo(generacion, individuoConfiguracion);
            case AVANZADO -> individuo = new AvanzadoIndividuo(generacion, individuoConfiguracion);
        }
        return individuo;
    }

    @Override
    public TipoIndividuo getTipoIndividuoDeComboLabel(String label) {
        final String itemNombre = StringUtils.remove(label, INDIVIDUAL);
        return TipoIndividuo.valueOf(itemNombre);
    }

    @Override
    public void reproducir(long generacion, Casilla casilla) {
        final List<Individuo> individuos = casilla.getIndividuos();
        // Ordenar los individuos por tipo, ordinal hace referencia al orden en el que se declaran los
        // distintos tipos en la enum IndividualType, esto se hace para dejar siempre los más avanzados
        // primero, puesto que se requiere que 2 individuos de distinto tipo tienen un tanto por ciento
        // de probabilidad de generar un individuo del tipo más avanzado, de este modo se asegura de
        // que el más avanzado esté siempre a la izquierda al crear en el siguiente paso las parejas
        // reproductoras, además, se comprueba que el individuo no se haya reproducido en el ciclo actual.
        final List<Individuo> individuosOrdenados = individuos.stream()
                .sorted(Comparator.comparing(individuo -> individuo.getTipoIndividuo().ordinal()))
                .toList();

        // Crear las parejas reproductoras, la lógica mira a ver si el número total de orderedIndividuals es
        // par o impar para saber cuántas parejas hay que crear.
        final List<Pair<Individuo, Individuo>> parIndividuos = new ArrayList<>();
        final int limite = individuosOrdenados.size() % 2 == 0 ? individuosOrdenados.size() : individuosOrdenados.size() -1;
        for (int i = 0; i < limite; i += 2){
            parIndividuos.add(Pair.of(individuosOrdenados.get(i), individuosOrdenados.get(i+1)));
        }

        parIndividuos.forEach(individuoPair -> {
            final Individuo izquierda = individuoPair.getLeft();
            final Individuo derecha = individuoPair.getRight();

            final BigDecimal izquierdaProbabilidadReproduccion = izquierda.getProbabilidadReproduccion();
            final double izquierdaNumeroElegido = new Random().doubles(0.0,1.0).findFirst().getAsDouble();
            final boolean izquierdaPuedeReproducirse = isInRange(0.0, izquierdaProbabilidadReproduccion.doubleValue(),izquierdaNumeroElegido);

            final BigDecimal derechaProbabilidadReproduccion = derecha.getProbabilidadReproduccion();
            final double derechaNumeroElegido = new Random().doubles(0.0,1.0).findFirst().getAsDouble();
            final boolean derechaPuedeReproducirse = isInRange(0.0, derechaProbabilidadReproduccion.doubleValue(),derechaNumeroElegido);

            // Si la probabilidad de reproducción ha sido positiva tanto para el individuo izquierdo como para el derecho,
            // se crea un nuevo individuo del tipo de la izquierda, puesto que es el tipo más alto.
            // TODO: Hacer que esto sea afectado por la probabilidad de mejora de población.
            if (izquierdaPuedeReproducirse && derechaPuedeReproducirse) {
                final Individuo nuevoIndividuo = getIndividuo(generacion, izquierda.getTipoIndividuo(),configuracionServicio.getConfiguracion().getIndividuoConfiguracion());
                final List<Individuo> individuosActualizados = new ArrayList<>(casilla.getIndividuos());
                individuosActualizados.add(nuevoIndividuo);
                casilla.setIndividuos(individuosActualizados);
                eventoServicio.addEvento(eventoMapper.mapIndividuoReproduceEvento(generacion, izquierda, derecha, nuevoIndividuo, casilla));
                eventoServicio.addEvento(eventoMapper.mapIndividuoReproduceEvento(generacion, derecha, izquierda, nuevoIndividuo, casilla));
                eventoServicio.addEvento(eventoMapper.mapIndividuoApareceEvento(generacion, nuevoIndividuo, casilla));
            }else {
                // Mueren los 2 porque no se pueden reproducir, este requisito parece un poco duro, pienso
                // que habría que eliminarlo porque limita muchísimo las posibilidades de una colonia de mayor
                // duración. Este requisito es bastante durillo para los individuos ADVANCED porque suelen
                // ir por parejas hacia el mismo recurso, aumentando sus posibilidades de morir por no poder reproducirse.
                final List<Individuo> individuosActualizados = new ArrayList<>(casilla.getIndividuos());
                individuosActualizados.removeIf((individuo -> {
                    eventoServicio.addEvento(eventoMapper.mapIndividuoMuereEvento(generacion, individuo, casilla, RazonMuerte.NO_REPRODUCE));
                    return individuo.getUuid().equals(izquierda.getUuid()) || individuo.getUuid().equals(derecha.getUuid());
                }));
                casilla.setIndividuos(individuosActualizados);
            }
        });
    }

    @Override
    public void clonacion(long generacion, Casilla casilla) {
        casilla.getIndividuos().forEach(individuo -> {
            final BigDecimal probabilidadClonacion = individuo.getProbabilidadClonacion();
            final double numeroElegido = new Random().doubles(0.0,1.0).findFirst().getAsDouble();
            if (isInRange(0.0,probabilidadClonacion.doubleValue(),numeroElegido)) {
                final Individuo individuoClonado = getIndividuoClonado(generacion, individuo, configuracionServicio.getConfiguracion().getIndividuoConfiguracion());
                final List<Individuo> individuosActualizados = new ArrayList<>(casilla.getIndividuos());
                individuosActualizados.add(individuoClonado);
                casilla.setIndividuos(individuosActualizados);
            }
        });
    }

    @Override
    public void senicidio(Casilla casilla) {
        final JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();
        final List<Individuo> individuos = new ArrayList<>(casilla.getIndividuos());
        if (individuos.size() > juegoConfiguracion.getMaxIndividuosPorCasilla()) {
            individuos.sort(Comparator.comparing(Individuo::getVida));
            final int individuosAEliminar = individuos.size() - juegoConfiguracion.getMaxIndividuosPorCasilla();
            individuos.subList(0, individuosAEliminar).clear();
            casilla.setIndividuos(individuos);
        }
    }


    @Override
    public void actualizarVidaIndividuos(long generacion, Casilla casilla) {
        casilla.getIndividuos().forEach(individuo -> {
            // Reducir la vida del individuo en cada turno
            final boolean isVivo = actualizarVida(-1,individuo);
            if (!isVivo) {
                eliminarIndividuo(casilla,individuo);
                eventoServicio.addEvento(eventoMapper.mapIndividuoMuereEvento(generacion, individuo, casilla, RazonMuerte.NO_VIDA));
            }else {
                final BigDecimal reproduccionProbabilidad = individuo.getProbabilidadReproduccion();
                final BigDecimal nuevaReproduccionProbabilidad = reproduccionProbabilidad.subtract(reproduccionProbabilidad.multiply(BigDecimal.valueOf(0.1)));
                individuo.setProbabilidadReproduccion(BigDecimal.valueOf(clamp(nuevaReproduccionProbabilidad.doubleValue(), 0.0, 1.0)));

                final BigDecimal clonacionProbabilidad = individuo.getProbabilidadClonacion();
                final BigDecimal nuevaClonacionProbabilidad = clonacionProbabilidad.subtract(clonacionProbabilidad.multiply(BigDecimal.valueOf(0.1)));
                individuo.setProbabilidadClonacion(BigDecimal.valueOf(clamp(nuevaClonacionProbabilidad.doubleValue(), 0.0, 1.0)));

                final BigDecimal muerteProbabilidad = BigDecimal.ONE.subtract(individuo.getProbabilidadReproduccion());
                individuo.setProbabilidadMuerte(BigDecimal.valueOf(clamp(muerteProbabilidad.doubleValue(),0.0, 1.0)));

                // Los individuos tienen una probabilidad de muerte en cada turno, comprobar:
                final double muerteSubitaProbabilidad = new Random().doubles(0.0, 1.0).findFirst().getAsDouble();
                if (isInRange(0.0, individuo.getProbabilidadMuerte().doubleValue(), muerteSubitaProbabilidad)){
                    eliminarIndividuo(casilla,individuo);
                    eventoServicio.addEvento(eventoMapper.mapIndividuoMuereEvento(generacion, individuo, casilla, RazonMuerte.PROBABILIDAD_MUERTE));
                }
            }
        });
    }

    @Override
    public void eliminarIndividuo(Casilla casilla, Individuo individuo) {
        final List<Individuo> individuos = new ArrayList<>(casilla.getIndividuos());
        individuos.remove(individuo);
        casilla.setIndividuos(individuos);
    }

    private Individuo getIndividuoClonado(Long generacion, Individuo individuo, IndividuoConfiguracion individuoConfiguracion) {
        Individuo individuoClonado = new BasicoIndividuo(generacion,individuoConfiguracion);
        switch (individuo.getTipoIndividuo()) {
            case NORMAL -> individuoClonado = new NormalIndividuo(generacion,individuoConfiguracion);
            case AVANZADO -> individuoClonado = new AvanzadoIndividuo(generacion,individuoConfiguracion);
        }
        individuoClonado.setProbabilidadReproduccion(individuo.getProbabilidadReproduccion());
        individuoClonado.setProbabilidadClonacion(individuo.getProbabilidadClonacion());
        individuoClonado.setProbabilidadMuerte(individuo.getProbabilidadMuerte());
        individuoClonado.setVida(individuo.getVida());
        individuoClonado.setDestinoPosicion(new Point2D<>(0.0,0.0));
        individuoClonado.setDireccion(new Point2D<>(0.0,0.0));
        return individuoClonado;
    }

    /**
     * Devuelve true si el individuo está vivo.
     */
    private boolean actualizarVida(Integer amount, Individuo individuo){
        final int vidaActualizada = individuo.getVida() + amount;
        individuo.setVida(clamp(vidaActualizada,0,10));
        return vidaActualizada > 0;
    }
}
