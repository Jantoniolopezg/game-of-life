package net.gameoflife.objetos.fxml;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoTablero;

import java.awt.*;

@Getter
@Setter
@Builder
public class UserData {
    TipoTablero tipoTablero;
    private Canvas canvas;
    private Label generationLabel;
}
