package net.gameoflife.servicios.impl;

import net.gameoflife.excepcion.GameOfLifeException;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.servicios.ConfiguracionServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfiguracionServicioImplTest {

    private ConfiguracionServicio configuracionServicio;

    @BeforeEach
    void setUp() {
        configuracionServicio = new ConfiguracionServicioImpl();
        try {
            Files.delete(Path.of("src/test/resources/saved-file.json"));
            Files.delete(Path.of("src/test/resources/test-file.json"));
            Files.delete(Path.of("src/test/resources/empty.json"));
        } catch (Exception ignored) {
        }
    }

    @Test
    void shouldSaveConfigurationFile() {
        Path savedFile = Path.of("src/test/resources/saved-file.json");
        assertFalse(Files.exists(savedFile));
        configuracionServicio.load(savedFile);
        assertNotNull(configuracionServicio.getConfiguracion());
        configuracionServicio.save(savedFile);
        assertTrue(Files.exists(savedFile));
    }

    @Test
    void shouldLoadCorrectFile() {
        configuracionServicio.load(Path.of("src/test/resources/existing.json"));
        JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();
        assertEquals(9, juegoConfiguracion.getSize().getX());
        assertEquals(9, juegoConfiguracion.getSize().getY());
    }

    @Test
    void shouldLoadEmptyFile() {
        configuracionServicio.load(Path.of("src/test/resources/empty.json"));
        Configuracion configuracion = configuracionServicio.getConfiguracion();
        assertNotNull(configuracion);
        assertNull(configuracion.getJuegoConfiguracion());
        assertNull(configuracion.getRecursoConfiguracion());
        assertNull(configuracion.getIndividuoConfiguracion());
    }

    @Test
    void shouldLoadDefaultsIfConfigFileIsNotFound() {
        configuracionServicio.load(Path.of("src/test/resources/non-existing.json"));
        Configuracion configuracion = configuracionServicio.getConfiguracion();
        JuegoConfiguracion juegoConfiguracion = configuracion.getJuegoConfiguracion();
        assertEquals(ConfiguracionServicio.DEFAULT_GAME_WIDTH, juegoConfiguracion.getSize().getX());
        assertEquals(ConfiguracionServicio.DEFAULT_GAME_HEIGHT, juegoConfiguracion.getSize().getY());
    }

    @Test
    void saveShouldThrowExceptionWithANullPath() {
        assertThrows(GameOfLifeException.class, () -> {
            configuracionServicio.save(null);
        });
    }

    @Test
    void saveShouldThrowExceptionWithANullConfiguration() {
        assertThrows(GameOfLifeException.class, () -> {
            configuracionServicio.save(Path.of("src/test/resources/empty.json"));
        });
    }

    @Test
    void saveShouldWorkCreatingAnEmptyJSONObject() {
        configuracionServicio.load(Path.of("src/test/resources/empty.json"));
        assertNotNull(configuracionServicio.getConfiguracion());
        configuracionServicio.save(Path.of("src/test/resources/empty.json"));
        assertTrue(Files.exists(Path.of("src/test/resources/empty.json")));
    }

}