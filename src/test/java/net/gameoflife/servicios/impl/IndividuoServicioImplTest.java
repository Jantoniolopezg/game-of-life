package net.gameoflife.servicios.impl;

import fixtures.ConfigurationFixture;
import fixtures.IndividuoConfiguracionFixture;
import fixtures.IndividuoFixture;
import fixtures.JuegoConfiguracionFixture;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.servicios.EventoMapper;
import net.gameoflife.servicios.EventoServicio;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndividuoServicioImplTest {

    @Mock
    private ConfiguracionServicio configuracionServicio;

    @Mock
    private EventoMapper eventoMapper;

    @Mock
    private EventoServicio eventoServicio;

    @InjectMocks
    private IndividuoServicioImpl individuoServicio;

    @Test
    void getRandomIndividual() {
        IndividuoConfiguracion individualConfiguration = IndividuoConfiguracionFixture.builder()
                .individualLife(100)
                .cloningProbability(BigDecimal.valueOf(0.9))
                .reproductionProbability(BigDecimal.valueOf(0.8))
                .build();
        Individuo result = individuoServicio.getRandomIndividuo(15L, individualConfiguration);
        assertEquals(100, result.getVida());
        assertEquals(0.9, result.getProbabilidadClonacion().doubleValue());
        assertEquals(0.8, result.getProbabilidadReproduccion().doubleValue());
        assertEquals(15L, result.getGeneracion());
        assertEquals(1.0 - result.getProbabilidadReproduccion().doubleValue(), result.getProbabilidadMuerte().doubleValue());
        assertNotSame(TipoIndividuo.NINGUNO, result.getTipoIndividuo());
        UUID uuid = UUID.fromString(String.valueOf(result.getUuid()));
        assertEquals(36L, uuid.toString().length());
    }

    @Test
    void getIndividual() {
        IndividuoConfiguracion basicIndividuoConfiguracion = IndividuoConfiguracionFixture.builder()
                .individualLife(100)
                .cloningProbability(BigDecimal.valueOf(0.9))
                .reproductionProbability(BigDecimal.valueOf(0.8))
                .build();
        IndividuoConfiguracion normalIndividuoConfiguracion = IndividuoConfiguracionFixture.builder()
                .individualLife(110)
                .cloningProbability(BigDecimal.valueOf(1.0))
                .reproductionProbability(BigDecimal.valueOf(0.9))
                .build();
        IndividuoConfiguracion advancedIndividuoConfiguracion = IndividuoConfiguracionFixture.builder()
                .individualLife(120)
                .cloningProbability(BigDecimal.valueOf(1.1))
                .reproductionProbability(BigDecimal.valueOf(1.2))
                .build();
        Individuo basicResult = individuoServicio.getIndividuo(15L, TipoIndividuo.BASICO, basicIndividuoConfiguracion);
        Individuo normalResult = individuoServicio.getIndividuo(20L, TipoIndividuo.NORMAL, normalIndividuoConfiguracion);
        Individuo advancedResult = individuoServicio.getIndividuo(25L, TipoIndividuo.AVANZADO, advancedIndividuoConfiguracion);

        assertEquals(100, basicResult.getVida());
        assertEquals(0.9, basicResult.getProbabilidadClonacion().doubleValue());
        assertEquals(0.8, basicResult.getProbabilidadReproduccion().doubleValue());
        assertEquals(15L, basicResult.getGeneracion());
        assertEquals(1.0 - basicResult.getProbabilidadReproduccion().doubleValue(), basicResult.getProbabilidadMuerte().doubleValue());
        assertEquals(TipoIndividuo.BASICO, basicResult.getTipoIndividuo());
        UUID basicUuid = UUID.fromString(String.valueOf(basicResult.getUuid()));
        assertEquals(36L, basicUuid.toString().length());

        assertEquals(110, normalResult.getVida());
        assertEquals(1.0, normalResult.getProbabilidadClonacion().doubleValue());
        assertEquals(0.9, normalResult.getProbabilidadReproduccion().doubleValue());
        assertEquals(20L, normalResult.getGeneracion());
        assertEquals(1.0 - normalResult.getProbabilidadReproduccion().doubleValue(), normalResult.getProbabilidadMuerte().doubleValue());
        assertEquals(TipoIndividuo.NORMAL, normalResult.getTipoIndividuo());
        UUID normalUuid = UUID.fromString(String.valueOf(normalResult.getUuid()));
        assertEquals(36L, normalUuid.toString().length());

        assertEquals(120, advancedResult.getVida());
        assertEquals(1.1, advancedResult.getProbabilidadClonacion().doubleValue());
        assertEquals(1.2, advancedResult.getProbabilidadReproduccion().doubleValue());
        assertEquals(25L, advancedResult.getGeneracion());
        assertEquals(1.0 - advancedResult.getProbabilidadReproduccion().doubleValue(), advancedResult.getProbabilidadMuerte().doubleValue());
        assertEquals(TipoIndividuo.AVANZADO, advancedResult.getTipoIndividuo());
        UUID advancedUuid = UUID.fromString(String.valueOf(advancedResult.getUuid()));
        assertEquals(36L, advancedUuid.toString().length());
    }

    @ParameterizedTest
    @MethodSource("labelsProvider")
    void getIndividualTypeFromComboLabel(String label, TipoIndividuo expectedType) {
        TipoIndividuo result = individuoServicio.getTipoIndividuoDeComboLabel(label);
        assertEquals(expectedType, result);
    }

    @Test
    void singleIndividualCannotReproduce() {
        Individuo individualA = IndividuoFixture.builder()
                .generation(9L)
                .reproductionProbability(BigDecimal.ONE)
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.BASICO);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(individualA))
                .build();
        individuoServicio.reproducir(10L, casilla);
        assertEquals(1, casilla.getIndividuos().size());
    }

    @Test
    void threeIndividualsOnlyTwoReproduceAndChildHasHighestType() {
        Configuracion configuration = ConfigurationFixture.builder().build();
        Individuo individualA = IndividuoFixture.builder()
                .generation(9L)
                .reproductionProbability(BigDecimal.ONE)
                .build(TipoIndividuo.NORMAL);
        Individuo individualB = IndividuoFixture.builder()
                .generation(8L)
                .reproductionProbability(BigDecimal.ONE)
                .build(TipoIndividuo.NORMAL);
        Individuo individualC = IndividuoFixture.builder()
                .generation(10L)
                .reproductionProbability(BigDecimal.ONE)
                .build(TipoIndividuo.AVANZADO);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(individualA, individualB, individualC))
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuration);
        individuoServicio.reproducir(11L, casilla);
        assertEquals(4, casilla.getIndividuos().size());
        assertEquals(TipoIndividuo.AVANZADO, casilla.getIndividuos().get(3).getTipoIndividuo());
    }

    @Test
    void sameIndividualsReproduceSameIndividual() {
        Configuracion configuracion = ConfigurationFixture.builder().build();
        Individuo individualA = IndividuoFixture.builder()
                .generation(9L)
                .reproductionProbability(BigDecimal.ONE)
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.NORMAL);
        Individuo individualB = IndividuoFixture.builder()
                .generation(9L)
                .reproductionProbability(BigDecimal.ONE)
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.NORMAL);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(individualA, individualB))
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuracion);

        assertEquals(2, casilla.getIndividuos().size());
        individuoServicio.reproducir(10L, casilla);
        assertEquals(3, casilla.getIndividuos().size());
        Individuo child = casilla.getIndividuos().get(2);
        assertEquals(TipoIndividuo.NORMAL, child.getTipoIndividuo());
        assertEquals(10L, child.getGeneracion());
        assertEquals(BigDecimal.valueOf(0.5), child.getProbabilidadReproduccion());
        assertTrue(child.getUuid() != individualA.getUuid() && child.getUuid() != individualB.getUuid());
    }

    @Test
    void sameIndividualsCannotReproduceAndDie() {
        Individuo individualA = IndividuoFixture.builder()
                .generation(9L)
                .reproductionProbability(BigDecimal.ZERO)
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.AVANZADO);
        Individuo individualB = IndividuoFixture.builder()
                .generation(9L)
                .reproductionProbability(BigDecimal.ZERO)
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.NORMAL);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(individualA, individualB))
                .build();
        assertEquals(2, casilla.getIndividuos().size());
        individuoServicio.reproducir(10L, casilla);
        assertEquals(0, casilla.getIndividuos().size());
    }


    @Test
    void individualShouldClone() {
        IndividuoConfiguracion individuoConfiguracion = IndividuoConfiguracion.builder()
                .reproduccionProbabilidad(BigDecimal.ONE)
                .clonacionProbabilidad(BigDecimal.ZERO)
                .build();
        Configuracion configuracion = ConfigurationFixture.builder()
                .individuoConfiguracion(individuoConfiguracion)
                .build();
        Individuo individualA = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.BASICO);
        Individuo individualB = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.ZERO)
                .build(TipoIndividuo.BASICO);
        List<Individuo> individuals = List.of(individualA, individualB);
        Casilla casilla = Casilla.builder()
                .recursos(new ArrayList<>())
                .individuos(individuals)
                .build();

        when(configuracionServicio.getConfiguracion()).thenReturn(configuracion);

        assertEquals(2, casilla.getIndividuos().size());
        individuoServicio.clonacion(1L, casilla);
        assertEquals(3, casilla.getIndividuos().size());
    }

    @Test
    void noneIndividualShouldClone() {
        Individuo individualA = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.ZERO)
                .build(TipoIndividuo.BASICO);
        Individuo individualB = IndividuoFixture.builder()
                .cloningProbability(BigDecimal.ZERO)
                .build(TipoIndividuo.BASICO);
        List<Individuo> individuals = List.of(individualA, individualB);
        Casilla casilla = Casilla.builder()
                .recursos(new ArrayList<>())
                .individuos(individuals)
                .build();

        individuoServicio.clonacion(1L, casilla);
        assertEquals(2, casilla.getIndividuos().size());
    }


    @Test
    void clonedIndividualShouldKeepSamePropertiesExceptUUIDAndGeneration() {
        IndividuoConfiguracion individuoConfiguracion = IndividuoConfiguracion.builder()
                .reproduccionProbabilidad(BigDecimal.valueOf(0.75))
                .vidaIndividuo(7)
                .clonacionProbabilidad(BigDecimal.ONE)
                .build();
        Configuracion configuracion = ConfigurationFixture.builder()
                .individuoConfiguracion(individuoConfiguracion)
                .build();
        Individuo individualA = IndividuoFixture.builder()
                .generation(10L)
                .cloningProbability(BigDecimal.ONE)
                .build(TipoIndividuo.BASICO);
        List<Individuo> individuals = List.of(individualA);
        Casilla casilla = Casilla.builder()
                .recursos(new ArrayList<>())
                .individuos(individuals)
                .build();

        when(configuracionServicio.getConfiguracion()).thenReturn(configuracion);

        individuoServicio.clonacion(15L, casilla);
        assertEquals(2, casilla.getIndividuos().size());
        assertNotEquals(individualA.getUuid(), casilla.getIndividuos().get(1).getUuid());
        assertNotEquals(individualA.getGeneracion(), casilla.getIndividuos().get(1).getGeneracion());
        assertEquals(individualA.getTipoIndividuo(), casilla.getIndividuos().get(1).getTipoIndividuo());
        assertEquals(individualA.getVida(), casilla.getIndividuos().get(1).getVida());
        assertEquals(individualA.getProbabilidadClonacion(), casilla.getIndividuos().get(1).getProbabilidadClonacion());
        assertEquals(individualA.getProbabilidadReproduccion(), casilla.getIndividuos().get(1).getProbabilidadReproduccion());
        assertEquals(individualA.getProbabilidadMuerte(), casilla.getIndividuos().get(1).getProbabilidadMuerte());
    }

    @Test
    void senicide() {
        Individuo basic = IndividuoFixture.builder().life(2).build(TipoIndividuo.BASICO);
        Individuo normal = IndividuoFixture.builder().life(3).build(TipoIndividuo.NORMAL);
        Individuo advanced = IndividuoFixture.builder().life(1).build(TipoIndividuo.AVANZADO);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(basic, normal, advanced))
                .build();
        Configuracion configuracion = ConfigurationFixture.builder()
                .juegoConfiguracion(JuegoConfiguracionFixture.builder()
                        .maxIndividuosPorCasilla(1)
                        .build())
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuracion);

        individuoServicio.senicidio(casilla);

        assertEquals(1, casilla.getIndividuos().size());
        assertEquals(3, casilla.getIndividuos().get(0).getVida());
    }

    @Test
    void senicideShouldNotKillAnyIndividual() {
        Individuo basic = IndividuoFixture.builder().life(2).build(TipoIndividuo.BASICO);
        Individuo normal = IndividuoFixture.builder().life(3).build(TipoIndividuo.NORMAL);
        Individuo advanced = IndividuoFixture.builder().life(1).build(TipoIndividuo.AVANZADO);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(basic, normal, advanced))
                .build();
        Configuracion configuracion = ConfigurationFixture.builder()
                .juegoConfiguracion(JuegoConfiguracionFixture.builder()
                        .maxIndividuosPorCasilla(3)
                        .build())
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuracion);

        individuoServicio.senicidio(casilla);

        assertEquals(3, casilla.getIndividuos().size());
    }

    @Test
    void senicideShouldKillAll() {
        Individuo basic = IndividuoFixture.builder().life(2).build(TipoIndividuo.BASICO);
        Individuo normal = IndividuoFixture.builder().life(3).build(TipoIndividuo.NORMAL);
        Individuo advanced = IndividuoFixture.builder().life(1).build(TipoIndividuo.AVANZADO);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(basic, normal, advanced))
                .build();
        Configuracion configuracion = ConfigurationFixture.builder()
                .juegoConfiguracion(JuegoConfiguracionFixture.builder()
                        .maxIndividuosPorCasilla(0)
                        .build())
                .build();
        when(configuracionServicio.getConfiguracion()).thenReturn(configuracion);

        individuoServicio.senicidio(casilla);

        assertEquals(0, casilla.getIndividuos().size());
    }

    @Test
    void removeIndividual() {
        Individuo individualA = IndividuoFixture.builder().build(TipoIndividuo.NORMAL);
        Individuo individualB = IndividuoFixture.builder().build(TipoIndividuo.BASICO);
        Casilla casilla = Casilla.builder()
                .individuos(List.of(individualA, individualB))
                .build();
        assertEquals(2, casilla.getIndividuos().size());
        individuoServicio.eliminarIndividuo(casilla, individualB);
        assertEquals(1, casilla.getIndividuos().size());
        assertEquals(individualA.getTipoIndividuo(), casilla.getIndividuos().get(0).getTipoIndividuo());
    }

    private static Stream<Arguments> labelsProvider() {
        return Stream.of(
                Arguments.of("Individuo: BASICO", TipoIndividuo.BASICO),
                Arguments.of("Individuo: NORMAL", TipoIndividuo.NORMAL),
                Arguments.of("Individuo: AVANZADO", TipoIndividuo.AVANZADO)
        );
    }

}