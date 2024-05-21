package net.gameoflife.servicios.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.excepcion.GameOfLifeException;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.ConfiguracionServicio;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

import static net.gameoflife.Constantes.Constantes.GAME_OF_LIFE_CFG;

@Slf4j
@Service
@RequiredArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConfiguracionServicioImpl implements ConfiguracionServicio {

    @Getter
    private Configuracion configuracion;

    /**
     * Carga un fichero de configuración y rellena el objeto Configuration que la contiene, en caso de que haya algún
     * problema como por ejemplo que el fichero no exista rellena el objeto Configuration con los valores por defecto.
     */
    @Override
    public void load(Path path) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            configuracion = mapper.readValue(path.toFile(), Configuracion.class);
        } catch (Exception exception){
            final String mensajeError = "Ha habido un problema cargando el fichero de configuracion.";
            log.error(mensajeError,exception);
            configuracion = getConfiguracionDefault();
            save(Path.of(GAME_OF_LIFE_CFG));
        }
    }

    /**
     * Guarda el objeto Configuration como un JSON en la ruta proporcionada.
     */
    @Override
    public void save(Path path) {
        try {
            if (configuracion == null){
                throw new GameOfLifeException("El objeto de configuracion no puede ser null");
            }
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(path.toFile(),configuracion);
        } catch (Exception exception){
            final String mensajeError = "Ha habido un error guardando el fichero de configuracion.";
            log.error(mensajeError,exception);
            throw new GameOfLifeException(mensajeError,exception);
        }
    }

    /**
     * Devuelve la configuración por defecto. (Ver método load()).
     */
    private Configuracion getConfiguracionDefault() {
        final JuegoConfiguracion juegoConfiguracion = JuegoConfiguracion.builder()
                .size(new Point2D<>(DEFAULT_GAME_WIDTH, DEFAULT_GAME_HEIGHT))
                .factorMejoraPoblacion(DEFAULT_FACTOR_MEJORA_POBLACION)
                .maxIndividuosPorCasilla(DEFAULT_MAX_INDIVIDUOS_POR_CASILLA)
                .maxRecursosPorCasilla(DEFAULT_MAX_RECURSOS_POR_CASILLA)
                .build();
        final IndividuoConfiguracion individuoConfiguracion = IndividuoConfiguracion.builder()
                .vidaIndividuo(DEFAULT_VIDA_INICIAL_INDIVIDUO)
                .clonacionProbabilidad(DEFAULT_PROBABILIDAD_CLONACION_INICIAL)
                .reproduccionProbabilidad(DEFAULT_PROBABILIDAD_REPRODUCCION_INICIAL)
                .build();
        final RecursoConfiguracion recursoConfiguracion = RecursoConfiguracion.builder()
                .vidaRecursos(DEFAULT_VIDA_RECURSOS)
                .aguaProbabilidadRecurso(DEFAULT_AGUA_PROBABILIDAD_RECURSO)
                .comidaProbabilidadRecurso(DEFAULT_COMIDA_PROBABILIDAD_RECURSO)
                .monteProbabilidadRecurso(DEFAULT_MONTE_PROBABILIDAD_RECURSO)
                .tesoroProbabilidadRecurso(DEFAULT_TESORO_PROBABILIDAD_RECURSO)
                .bibliotecaProbabilidadRecurso(DEFAULT_BIBLIOTECA_PROBABILIDAD_RECURSO)
                .pozoProbabilidadRecurso(DEFAULT_POZO_PROBABILIDAD_RECURSO)
                .tesoroFactorMejoraReproduccion(DEFAULT_FACTOR_MEJORA_REPRODUCCION_TESORO)
                .bibliotecaFactorMejoraClonacion(DEFAULT_FACTOR_MEJORA_CLONACION_BIBLIOTECA)
                .build();
        return Configuracion.builder()
                .juegoConfiguracion(juegoConfiguracion)
                .individuoConfiguracion(individuoConfiguracion)
                .recursoConfiguracion(recursoConfiguracion)
                .build();
    }

}
