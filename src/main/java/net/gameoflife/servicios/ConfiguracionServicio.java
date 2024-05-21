package net.gameoflife.servicios;

import net.gameoflife.objetos.configuracion.Configuracion;

import java.math.BigDecimal;
import java.nio.file.Path;

public interface ConfiguracionServicio {
    Integer DEFAULT_GAME_WIDTH = 9;
    Integer DEFAULT_GAME_HEIGHT = 9;
    Integer DEFAULT_MAX_RECURSOS_POR_CASILLA = 5;
    Integer DEFAULT_MAX_INDIVIDUOS_POR_CASILLA = 5;
    Integer DEFAULT_VIDA_INICIAL_INDIVIDUO = 3;
    Integer DEFAULT_VIDA_RECURSOS = 3;
    BigDecimal DEFAULT_PROBABILIDAD_CLONACION_INICIAL = BigDecimal.valueOf(.5);
    BigDecimal DEFAULT_FACTOR_MEJORA_POBLACION = BigDecimal.valueOf(.5);
    BigDecimal DEFAULT_PROBABILIDAD_REPRODUCCION_INICIAL = BigDecimal.valueOf(.5);
    BigDecimal DEFAULT_FACTOR_MEJORA_CLONACION_BIBLIOTECA = BigDecimal.valueOf(.5);
    BigDecimal DEFAULT_FACTOR_MEJORA_REPRODUCCION_TESORO = BigDecimal.valueOf(.5);
    BigDecimal DEFAULT_AGUA_PROBABILIDAD_RECURSO = BigDecimal.valueOf(0.5);
    BigDecimal DEFAULT_COMIDA_PROBABILIDAD_RECURSO = BigDecimal.valueOf(0.5);
    BigDecimal DEFAULT_MONTE_PROBABILIDAD_RECURSO = BigDecimal.valueOf(0.5);
    BigDecimal DEFAULT_TESORO_PROBABILIDAD_RECURSO = BigDecimal.valueOf(0.5);
    BigDecimal DEFAULT_BIBLIOTECA_PROBABILIDAD_RECURSO = BigDecimal.valueOf(0.5);
    BigDecimal DEFAULT_POZO_PROBABILIDAD_RECURSO = BigDecimal.valueOf(0.25);

    void load(Path path);

    void save(Path path);

    Configuracion getConfiguracion();
}
