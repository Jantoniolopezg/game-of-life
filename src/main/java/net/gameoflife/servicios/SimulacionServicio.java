package net.gameoflife.servicios;

import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.fxml.UserData;
import net.gameoflife.objetos.juego.CondicionesFinalizado;

public interface SimulacionServicio {

    void initGame(UserData userData);

    Casilla[][] getTablero();

    void setTablero(Casilla[][] tablero);

    void initTablero();

    void addElemento(Long value, Casilla casilla, String itemSeleccionado);

    void removeElemento(Long value, Casilla casilla, String itemSeleccionado);

    void runGameLoop(long generation);

    CondicionesFinalizado getCondicionesFinalizado(long generation);
}
