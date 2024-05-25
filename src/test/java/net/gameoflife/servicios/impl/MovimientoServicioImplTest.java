package net.gameoflife.servicios.impl;

import fixtures.IndividuoFixture;
import fixtures.RecursoFixture;
import fixtures.TableroFixture;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.ParametrosMovimiento;
import net.gameoflife.objetos.recursos.ExtendedRecurso;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.EventoMapper;
import net.gameoflife.servicios.EventoServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServicioImplTest {

    @InjectMocks
    private MovimientoServicioImpl movimientoServicio;

    @Mock
    private EventoServicio eventoServicio;

    @Mock
    private EventoMapper eventoMapper;

    @Test
    void moveBasicIndividual() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][0];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);

        Individuo individual = IndividuoFixture.builder()
                .direction(new Point2D<>(-1.0, -1.0))
                .build(TipoIndividuo.NORMAL);

        ParametrosMovimiento parametrosMovimiento = ParametrosMovimiento.builder()
                .casilla(Casilla.builder().individuos(List.of(individual)).build())
                .posicionIndividuo(new Point2D<>(3.0, 3.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();

        movimientoServicio.moveBasicoIndividuo(parametrosMovimiento, individual);
        assertEquals(0, board[3][3].getIndividuos().size());
    }

    @Test
    void updateIndividualDirectionAndTargetPositionUp() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][0];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);

        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.NORMAL);

        ParametrosMovimiento movementParameters = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 3.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();

        movimientoServicio.actualizarIndividuoDireccionYObjetivoPosicion(movementParameters, individual);

        assertEquals(0, individual.getDireccion().getX());
        assertEquals(0, individual.getDireccion().getY());
        assertEquals(0, individual.getDestinoPosicion().getX());
        assertEquals(0, individual.getDestinoPosicion().getY());
    }

    @Test
    void updateIndividualDirectionAndTargetPositionDown() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][6];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);

        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.NORMAL);

        ParametrosMovimiento movementParameters = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 3.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();

        movimientoServicio.actualizarIndividuoDireccionYObjetivoPosicion(movementParameters, individual);

        assertEquals(0, individual.getDireccion().getX());
        assertEquals(1, individual.getDireccion().getY());
        assertEquals(3, individual.getDestinoPosicion().getX());
        assertEquals(6, individual.getDestinoPosicion().getY());
    }

    @Test
    void updateIndividualDirectionAndTargetPositionLeft() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[0][6];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);

        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.NORMAL);

        ParametrosMovimiento movementParameters = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 6.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();

        movimientoServicio.actualizarIndividuoDireccionYObjetivoPosicion(movementParameters, individual);

        assertEquals(0, individual.getDireccion().getX());
        assertEquals(0, individual.getDireccion().getY());
        assertEquals(0, individual.getDestinoPosicion().getX());
        assertEquals(0, individual.getDestinoPosicion().getY());
    }

    @Test
    void updateIndividualDirectionAndTargetPositionRight() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[9][6];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);

        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.NORMAL);

        ParametrosMovimiento movementParameters = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 6.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();

        movimientoServicio.actualizarIndividuoDireccionYObjetivoPosicion(movementParameters, individual);

        assertEquals(1, individual.getDireccion().getX());
        assertEquals(0, individual.getDireccion().getY());
        assertEquals(9, individual.getDestinoPosicion().getX());
        assertEquals(6, individual.getDestinoPosicion().getY());
    }

    @Test
    void getResourcePositionShouldReturnVectorZero() {
        Casilla[][] board = TableroFixture.builder().build();
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.AVANZADO);

        ParametrosMovimiento movementParameters = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 3.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();

        Point2D<Double> resourcePosition = movimientoServicio.getRecursoPosicion(movementParameters, individual);

        assertTrue(resourcePosition.getX() == 0 || resourcePosition.getY() == 0);
    }

    @Test
    void getResourcePositionShouldWork() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][0];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);

        ParametrosMovimiento parametrosMovimiento = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 3.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.AVANZADO);
        Point2D<Double> resourcePosition = movimientoServicio.getRecursoPosicion(parametrosMovimiento, individual);

        assertEquals(3, resourcePosition.getX());
        assertEquals(0, resourcePosition.getY());
    }

    @Test
    void getResourcePositionShouldReturnClosestResource() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCellA = board[3][9];
        List<Recurso> resourcesA = new ArrayList<>();
        resourcesA.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCellA.setRecursos(resourcesA);

        Casilla targetCellB = board[3][8];
        List<Recurso> resourcesB = new ArrayList<>();
        resourcesB.add(RecursoFixture.builder().build(TipoRecurso.AGUA));
        targetCellB.setRecursos(resourcesB);

        Casilla targetCellC = board[3][7];
        List<Recurso> resourcesC = new ArrayList<>();
        resourcesC.add(RecursoFixture.builder().build(TipoRecurso.MONTE));
        targetCellC.setRecursos(resourcesC);

        ParametrosMovimiento parametrosMovimiento = ParametrosMovimiento.builder()
                .posicionIndividuo(new Point2D<>(3.0, 3.0))
                .tablero(board)
                .tableroSize(new Point2D<>(10, 10))
                .build();
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.AVANZADO);
        Point2D<Double> resourcePosition = movimientoServicio.getRecursoPosicion(parametrosMovimiento, individual);

        assertEquals(3, resourcePosition.getX());
        assertEquals(9, resourcePosition.getY());
    }

    @Test
    void getResourcesAndDistanceShouldWork() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][0];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);
        Point2D<Double> sourcePoint = new Point2D<>(3.0, 5.0);
        Point2D<Double> targetPoint = new Point2D<>(3.0, 0.0);
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.AVANZADO);

        List<ExtendedRecurso> result = movimientoServicio.getRecursosYDistancia(board, sourcePoint, targetPoint, individual.getTipoIndividuo());

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getDistance());
        assertEquals(3, result.get(0).getPosition().getX());
        assertEquals(0, result.get(0).getPosition().getY());
        assertEquals(TipoRecurso.COMIDA, result.get(0).getRecurso().getTipoRecurso());
    }

    @Test
    void getResourcesAndDistanceShouldNotReturnResourcesInSameCell() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][5];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.COMIDA));
        targetCell.setRecursos(resources);
        Point2D<Double> sourcePoint = new Point2D<>(3.0, 5.0);
        Point2D<Double> targetPoint = new Point2D<>(3.0, 5.0);
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.AVANZADO);

        List<ExtendedRecurso> result = movimientoServicio.getRecursosYDistancia(board, sourcePoint, targetPoint, individual.getTipoIndividuo());

        assertEquals(0, result.size());
    }

    @Test
    void getResourcesAndDistanceShouldNotFindAnything() {
        Casilla[][] board = TableroFixture.builder().build();
        Casilla targetCell = board[3][0];
        List<Recurso> resources = new ArrayList<>();
        resources.add(RecursoFixture.builder().build(TipoRecurso.POZO));
        targetCell.setRecursos(resources);
        Point2D<Double> sourcePoint = new Point2D<>(3.0, 5.0);
        Point2D<Double> targetPoint = new Point2D<>(3.0, 0.0);
        Individuo individual = IndividuoFixture.builder().build(TipoIndividuo.AVANZADO);

        List<ExtendedRecurso> result = movimientoServicio.getRecursosYDistancia(board, sourcePoint, targetPoint, individual.getTipoIndividuo());

        assertEquals(0, result.size());
    }
}