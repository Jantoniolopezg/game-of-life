package net.gameoflife.servicios.impl;

import fixtures.*;
import net.gameoflife.enumeraciones.TipoEvento;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.eventos.RecursoDesapareceEvento;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.servicios.EventoMapper;
import net.gameoflife.servicios.EventoServicio;
import net.gameoflife.servicios.IndividuoServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecursoServicioImplTest {

    @Mock
    private EventoMapper eventoMapper;

    @Mock
    private EventoServicio eventoServicio;

    @Mock
    private ConfiguracionServicio configuracionServicio;

    @Mock
    private IndividuoServicio individuoServicio;

    @InjectMocks
    private RecursoServicioImpl resourceService;


    @Test
    void applyResourceWater() {
        Individuo individual = IndividuoFixture.builder()
                .life(1)
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.AGUA);
        Casilla cell = Casilla.builder().individuos(List.of(individual)).recursos(List.of(resource)).build();
        resourceService.aplicarRecursos(cell);
        assertEquals(3, (int) cell.getIndividuos().get(0).getVida());
    }

    @Test
    void applyResourceFood() {
        Individuo individual = IndividuoFixture.builder()
                .life(-2)
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.COMIDA);
        Casilla cell = Casilla.builder().individuos(List.of(individual)).recursos(List.of(resource)).build();
        resourceService.aplicarRecursos(cell);
        assertEquals(8, (int) cell.getIndividuos().get(0).getVida());
    }

    @Test
    void applyResourceFoodCannotIncreaseLifeOver10() {
        Individuo individual = IndividuoFixture.builder()
                .life(9)
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.COMIDA);
        Casilla cell = Casilla.builder().individuos(List.of(individual)).recursos(List.of(resource)).build();
        resourceService.aplicarRecursos(cell);
        assertEquals(10, (int) cell.getIndividuos().get(0).getVida());
    }

    @Test
    void applyResourceMountain() {
        Individuo individual = IndividuoFixture.builder()
                .life(5)
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.MONTE);
        Casilla cell = Casilla.builder().individuos(List.of(individual)).recursos(List.of(resource)).build();
        resourceService.aplicarRecursos(cell);
        assertEquals(3, (int) cell.getIndividuos().get(0).getVida());
    }

    @Test
    void applyResourceTreasure() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .treasureReproductionImprovementFactor(BigDecimal.valueOf(1.0))
                .build();
        Configuracion configuration = ConfigurationFixture.builder()
                .recursoConfiguracion(resourceConfiguration)
                .build();
        Individuo individual = IndividuoFixture.builder()
                .reproductionProbability(BigDecimal.valueOf(0.2))
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.TESORO);
        Casilla cell = Casilla.builder()
                .individuos(List.of(individual))
                .recursos(List.of(resource))
                .build();

        when(configuracionServicio.getConfiguracion()).thenReturn(configuration);
        assertEquals(0.2, cell.getIndividuos().get(0).getProbabilidadReproduccion().doubleValue());
        resourceService.aplicarRecursos(cell);
        assertEquals(0.4, cell.getIndividuos().get(0).getProbabilidadReproduccion().doubleValue());
    }

    @Test
    void applyResourceLibrary() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .libraryCloningImprovementFactor(BigDecimal.valueOf(1.0))
                .build();
        Configuracion configuration = ConfigurationFixture.builder()
                .recursoConfiguracion(resourceConfiguration)
                .build();
        Individuo individual = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.valueOf(0.2))
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.BIBLIOTECA);
        Casilla cell = Casilla.builder()
                .individuos(List.of(individual))
                .recursos(List.of(resource))
                .build();

        when(configuracionServicio.getConfiguracion()).thenReturn(configuration);
        assertEquals(0.2, cell.getIndividuos().get(0).getProbabilidadClonacion().doubleValue());
        resourceService.aplicarRecursos(cell);
        assertEquals(0.4, cell.getIndividuos().get(0).getProbabilidadClonacion().doubleValue());
    }

    @Test
    void applyResourceDwell() {
        Individuo individual1 = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.valueOf(0.2))
                .build(TipoIndividuo.BASICO);
        Individuo individual2 = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.valueOf(0.2))
                .build(TipoIndividuo.BASICO);
        Individuo individual3 = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.valueOf(0.2))
                .build(TipoIndividuo.BASICO);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.POZO);
        Casilla cell = Casilla.builder()
                .individuos(List.of(individual1, individual2, individual3))
                .recursos(List.of(resource))
                .build();

        assertTrue(cell.getIndividuos().get(0).getVida() != 0);
        resourceService.aplicarRecursos(cell);
        assertEquals(0, (int) cell.getIndividuos().get(0).getVida());
    }

    @Test
    void getRandomResourceCreatesWaterResource() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.valueOf(6.0))
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.ZERO)
                .build();
        Recurso resource = resourceService.getRandomRecurso(1L, resourceConfiguration);
        assertEquals(1L, (long) resource.getGeneracion());
        assertEquals(3, resource.getVidaRecurso());
        assertEquals(TipoRecurso.AGUA, resource.getTipoRecurso());
    }

    @Test
    void getRandomResourceCreatesFoodResource() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.valueOf(6.0))
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.ZERO)
                .build();
        Recurso resource = resourceService.getRandomRecurso(1L, resourceConfiguration);
        assertEquals(1L, (long) resource.getGeneracion());
        assertEquals(3, resource.getVidaRecurso());
        assertEquals(TipoRecurso.COMIDA, resource.getTipoRecurso());
    }

    @Test
    void getRandomResourceCreatesMountainResource() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.valueOf(6.0))
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.ZERO)
                .build();
        Recurso resource = resourceService.getRandomRecurso(1L, resourceConfiguration);
        assertEquals(1L, (long) resource.getGeneracion());
        assertEquals(3, resource.getVidaRecurso());
        assertEquals(TipoRecurso.MONTE, resource.getTipoRecurso());
    }

    @Test
    void getRandomResourceCreatesTreasureResource() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.valueOf(6.0))
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.ZERO)
                .build();
        Recurso resource = resourceService.getRandomRecurso(1L, resourceConfiguration);
        assertEquals(1L, (long) resource.getGeneracion());
        assertEquals(3, resource.getVidaRecurso());
        assertEquals(TipoRecurso.TESORO, resource.getTipoRecurso());
    }

    @Test
    void getRandomResourceCreatesLibraryResource() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.valueOf(6.0))
                .dwellResourceProbability(BigDecimal.ZERO)
                .build();
        Recurso resource = resourceService.getRandomRecurso(1L, resourceConfiguration);
        assertEquals(1L, (long) resource.getGeneracion());
        assertEquals(3, resource.getVidaRecurso());
        assertEquals(TipoRecurso.BIBLIOTECA, resource.getTipoRecurso());
    }

    @Test
    void getRandomResourceCreatesDwellResource() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.valueOf(6.0))
                .build();
        Recurso resource = resourceService.getRandomRecurso(1L, resourceConfiguration);
        assertEquals(1L, (long) resource.getGeneracion());
        assertEquals(3, resource.getVidaRecurso());
        assertEquals(TipoRecurso.POZO, resource.getTipoRecurso());
    }

    @Test
    void getResourceShouldReturnWater() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder().build();
        Recurso resource = resourceService.getRecurso(11L, TipoRecurso.AGUA, resourceConfiguration);
        assertEquals(11L, (long) resource.getGeneracion());
        assertEquals(5, resource.getVidaRecurso());
        assertEquals(TipoRecurso.AGUA, resource.getTipoRecurso());
    }

    @Test
    void getResourceShouldReturnFood() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder().build();
        Recurso resource = resourceService.getRecurso(11L, TipoRecurso.COMIDA, resourceConfiguration);
        assertEquals(11L, (long) resource.getGeneracion());
        assertEquals(5, resource.getVidaRecurso());
        assertEquals(TipoRecurso.COMIDA, resource.getTipoRecurso());
    }

    @Test
    void getResourceShouldReturnMountain() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder().build();
        Recurso resource = resourceService.getRecurso(11L, TipoRecurso.MONTE, resourceConfiguration);
        assertEquals(11L, (long) resource.getGeneracion());
        assertEquals(5, resource.getVidaRecurso());
        assertEquals(TipoRecurso.MONTE, resource.getTipoRecurso());
    }

    @Test
    void getResourceShouldReturnTreasure() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder().build();
        Recurso resource = resourceService.getRecurso(11L, TipoRecurso.TESORO, resourceConfiguration);
        assertEquals(11L, (long) resource.getGeneracion());
        assertEquals(5, resource.getVidaRecurso());
        assertEquals(TipoRecurso.TESORO, resource.getTipoRecurso());
    }

    @Test
    void getResourceShouldReturnLibrary() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder().build();
        Recurso resource = resourceService.getRecurso(11L, TipoRecurso.BIBLIOTECA, resourceConfiguration);
        assertEquals(11L, (long) resource.getGeneracion());
        assertEquals(5, resource.getVidaRecurso());
        assertEquals(TipoRecurso.BIBLIOTECA, resource.getTipoRecurso());
    }

    @Test
    void getResourceShouldReturnDwell() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder().build();
        Recurso resource = resourceService.getRecurso(11L, TipoRecurso.POZO, resourceConfiguration);
        assertEquals(11L, (long) resource.getGeneracion());
        assertEquals(5, resource.getVidaRecurso());
        assertEquals(TipoRecurso.POZO, resource.getTipoRecurso());
    }


    @ParameterizedTest
    @MethodSource("labelsProvider")
    void getResourceTypeFromComboLabel(String label, TipoRecurso expectedType) {
        TipoRecurso result = resourceService.getTipoRecursoDeComboLabel(label);
        assertEquals(expectedType, result);
    }

    private static Stream<Arguments> labelsProvider() {
        return Stream.of(
                Arguments.of("Recurso: AGUA", TipoRecurso.AGUA),
                Arguments.of("Recurso: COMIDA", TipoRecurso.COMIDA),
                Arguments.of("Recurso: MONTE", TipoRecurso.MONTE),
                Arguments.of("Recurso: TESORO", TipoRecurso.TESORO),
                Arguments.of("Recurso: BIBLIOTECA", TipoRecurso.BIBLIOTECA),
                Arguments.of("Recurso: POZO", TipoRecurso.POZO)
        );
    }

    @Test
    void create() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.valueOf(6.0))
                .build();
        JuegoConfiguracion gameConfiguration = JuegoConfiguracionFixture.builder()
                .maxRecursosPorCasilla(3)
                .build();
        Configuracion configuration = ConfigurationFixture.builder()
                .recursoConfiguracion(resourceConfiguration)
                .juegoConfiguracion(gameConfiguration)
                .build();
        List<Recurso> resources = List.of(RecursoFixture.builder().resourceLife(5).build(TipoRecurso.COMIDA));
        List<Individuo> individuals = List.of(IndividuoFixture.builder().build(TipoIndividuo.BASICO));
        Casilla cell = Casilla.builder()
                .recursos(resources)
                .individuos(individuals)
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuration);
        assertEquals(1, cell.getRecursos().size());
        resourceService.crear(15L, cell);
        assertEquals(2, cell.getRecursos().size());
        assertEquals(TipoRecurso.COMIDA, cell.getRecursos().get(0).getTipoRecurso());
        assertEquals(5, cell.getRecursos().get(0).getVidaRecurso());
        assertEquals(TipoRecurso.POZO, cell.getRecursos().get(1).getTipoRecurso());
        assertEquals(3, cell.getRecursos().get(1).getVidaRecurso());
    }

    @Test
    void createDoesntAddEmptyResources() {
        RecursoConfiguracion resourceConfiguration = RecursoConfiguracionFixture.builder()
                .resourcesLife(3)
                .waterResourceProbability(BigDecimal.ZERO)
                .foodResourceProbability(BigDecimal.ZERO)
                .mountainResourceProbability(BigDecimal.ZERO)
                .treasureResourceProbability(BigDecimal.ZERO)
                .libraryResourceProbability(BigDecimal.ZERO)
                .dwellResourceProbability(BigDecimal.ZERO)
                .build();
        JuegoConfiguracion gameConfiguration = JuegoConfiguracionFixture.builder().build();
        Configuracion configuration = ConfigurationFixture.builder()
                .recursoConfiguracion(resourceConfiguration)
                .juegoConfiguracion(gameConfiguration)
                .build();
        Casilla cell = Casilla.builder()
                .recursos(new ArrayList<>())
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuration);

        assertEquals(0, cell.getRecursos().size());

        resourceService.crear(15L, cell);

        assertEquals(0, cell.getRecursos().size());
    }

    @Test
    void updateResourcesLifeShouldDecreaseLife() {
        List<Recurso> resources = List.of(RecursoFixture.builder().resourceLife(5).build(TipoRecurso.COMIDA));
        List<Individuo> individuals = List.of(IndividuoFixture.builder().build(TipoIndividuo.BASICO));
        Casilla cell = Casilla.builder()
                .recursos(resources)
                .individuos(individuals)
                .build();
        resourceService.actualizarVidaRecursos(1L, cell);
        assertEquals(4, cell.getRecursos().get(0).getVidaRecurso());
    }

    @Test
    void updateResourcesLifeShouldRemoveResource() {
        RecursoDesapareceEvento event = RecursoDesapareceEvento.builder()
                .posicion(new Point2D<>(0, 0))
                .uuid(UUID.randomUUID())
                .generation(0L)
                .tipoEvento(TipoEvento.RECURSO_DESAPARECE)
                .tipoRecurso(TipoRecurso.MONTE)
                .build();
        List<Recurso> resources = List.of(RecursoFixture.builder().resourceLife(1).build(TipoRecurso.COMIDA));
        List<Individuo> individuals = List.of(IndividuoFixture.builder().build(TipoIndividuo.BASICO));
        Casilla cell = Casilla.builder()
                .recursos(resources)
                .individuos(individuals)
                .build();
        when(eventoMapper.mapRecursoDesapareceEvento(anyLong(), any(), any())).thenReturn(event);
        resourceService.actualizarVidaRecursos(1L, cell);
        assertEquals(0, cell.getRecursos().size());
    }

}