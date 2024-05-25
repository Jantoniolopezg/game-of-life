package net.gameoflife.servicios.impl;

import fixtures.ConfigurationFixture;
import fixtures.IndividuoFixture;
import fixtures.JuegoConfiguracionFixture;
import fixtures.RecursoFixture;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.servicios.IndividuoServicio;
import net.gameoflife.servicios.RecursoServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CasillaServicioImplTest {

    @Mock
    private RecursoServicio recursoServicio;
    @Mock
    private IndividuoServicio individuoServicio;
    @Mock
    private ConfiguracionServicio configuracionServicio;
    @InjectMocks
    private CasillaServicioImpl casillaServicio;

    @Test
    void getRandomCell() {
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.NORMAL);
        Recurso resource = RecursoFixture.builder().build(TipoRecurso.TESORO);
        JuegoConfiguracion gameConfiguration = JuegoConfiguracionFixture.builder().maxIndividuosPorCasilla(3).maxRecursosPorCasilla(2).build();
        Configuracion configuration = ConfigurationFixture.builder().juegoConfiguracion(gameConfiguration).build();
        when(individuoServicio.getRandomIndividuo(anyLong(), any(IndividuoConfiguracion.class))).thenReturn(individual);
        when(recursoServicio.getRandomRecurso(anyLong(), any(RecursoConfiguracion.class))).thenReturn(resource);
        when(configuracionServicio.getConfiguracion()).thenReturn(configuration);

        Casilla result = casillaServicio.getRandomCasilla();
        Recurso resultingResource = result.getRecursos().get(0);
        Individuo resultingIndividual = result.getIndividuos().get(0);

        assertEquals(3, result.getIndividuos().size());
        assertEquals(2, result.getRecursos().size());
        assertEquals(TipoRecurso.TESORO, resultingResource.getTipoRecurso());
        assertEquals(TipoIndividuo.NORMAL, resultingIndividual.getTipoIndividuo());
    }

    @Test
    void getEmptyCell() {
        Casilla result = casillaServicio.getCasillaVacia();
        assertNotNull(result.getIndividuos());
        assertNotNull(result.getRecursos());
        assertEquals(0, result.getIndividuos().size());
        assertEquals(0, result.getRecursos().size());
    }

    @Test
    void getCellFromScreenPosition() {
        Casilla[][] board = new Casilla[2][2];
        Casilla cell = Casilla.builder()
                .individuos(List.of(IndividuoFixture.builder().build(TipoIndividuo.AVANZADO)))
                .build();
        board[1][1] = cell;
        Optional<Casilla> result = casillaServicio.getCasillaDePosicionEnPantalla(board, new Point2D<>(120.0, 120.0), new Point2D<>(160.0, 160.0));
        assertTrue(result.isPresent());
        assertEquals(TipoIndividuo.AVANZADO, cell.getIndividuos().get(0).getTipoIndividuo());
    }

    @Test
    void getCellFromScreenPositionOutOfLimitsShouldReturnEmptyOptional() {
        Casilla[][] board = new Casilla[1][1];
        Optional<Casilla> result = casillaServicio.getCasillaDePosicionEnPantalla(board, new Point2D<>(120.0, 120.0), new Point2D<>(80.0, 80.0));
        assertTrue(result.isEmpty());
    }

}