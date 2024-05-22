package net.gameoflife.objetos.fxml;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoTablero;

@Getter
@Setter
@Builder
public class UserData {
    TipoTablero tipoTablero;
    private Canvas canvas;
    private Label generationLabel;
}
