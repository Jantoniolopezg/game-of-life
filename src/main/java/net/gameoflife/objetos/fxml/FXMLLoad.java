package net.gameoflife.objetos.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class FXMLLoad {
    private Parent parent;
    private FXMLLoader fxmlLoader;
}
