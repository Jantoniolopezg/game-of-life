package net.gameoflife.servicios.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.excepcion.GameOfLifeException;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.servicios.PersistenciaJuegoServicio;
import net.gameoflife.utils.AlertUtil;
import org.springframework.stereotype.Service;

import java.io.*;

import static net.gameoflife.Constantes.Constantes.GAME_DATA_BIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersistenciaJuegoServicioImpl implements PersistenciaJuegoServicio {
    @Override
    public Casilla[][] load() {
        Casilla[][] casillas = null;
        final File file = new File(GAME_DATA_BIN);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GAME_DATA_BIN))) {
                casillas = (Casilla[][]) ois.readObject();
            }catch (Exception exception){
                final String mensajeError = "No se ha podido cargar la partida." + exception;
                log.error(mensajeError);
                throw new GameOfLifeException(mensajeError);
            }
        }else{
            AlertUtil.showInfo("El fichero con los datos de la partida no existe.");
        }
        return casillas;
    }

    @Override
    public void save(Casilla[][] tablero) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GAME_DATA_BIN))) {
            oos.writeObject(tablero);
        } catch (Exception exception){
            final String mensajeError = "No se ha podido guardar la partida." + exception;
            log.error(mensajeError);
            throw new GameOfLifeException(mensajeError);
        }
    }
}
