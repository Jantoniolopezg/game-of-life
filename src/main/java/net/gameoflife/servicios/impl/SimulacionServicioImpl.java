package net.gameoflife.servicios.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.fxml.UserData;
import net.gameoflife.servicios.CasillaServicio;
import net.gameoflife.servicios.SimulacionServicio;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimulacionServicioImpl implements SimulacionServicio {
    private final CasillaServicio casillaServicio;
    @Override
    public void initGame(UserData userData) {

    }

    @Override
    public Casilla[][] getTablero() {
        return new Casilla[0][];
    }

    @Override
    public void setTablero(Casilla[][] tablero) {

    }

    @Override
    public void initTablero() {

    }

    @Override
    public void addElemento(Long value, Casilla casilla, String itemSeleccionado) {

    }

    @Override
    public void removeElemento(Long value, Casilla casilla, String itemSeleccionado) {

    }

    @Override
    public void runGameLoop(long generation) {

    }
}
