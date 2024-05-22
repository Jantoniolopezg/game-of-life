package net.gameoflife.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import net.gameoflife.enumeraciones.TipoTablero;
import net.gameoflife.objetos.fxml.UserData;
import net.gameoflife.servicios.ConfiguracionServicio;
import org.springframework.stereotype.Controller;

import java.nio.file.Path;

import static net.gameoflife.Constantes.Constantes.GAME_OF_LIFE_CFG;

@Controller
@RequiredArgsConstructor
public class MainMenuController extends BaseController {

    @FXML
    private final SimulationController simulationController;

    private final ConfiguracionServicio configuracionServicio;

    @FXML
    private AnchorPane rootPane;

    public void onNewGameAction(javafx.event.ActionEvent actionEvent) {
        newGame(TipoTablero.RANDOM);
    }

    public void onNewEmptyGameAction(ActionEvent actionEvent) {
        newGame(TipoTablero.VACIO);
    }

    private void newGame(TipoTablero tipoTablero) {
        configuracionServicio.load(Path.of(GAME_OF_LIFE_CFG));
        final UserData userData = UserData.builder()
                .tipoTablero(tipoTablero)
                .canvas(simulationController.getGameCanvas())
                .generationLabel(simulationController.getGenerationLabel())
                .build();
        getStageManager().getPrimarySatge().setUserData(userData);
        final TabPane tabPane = (TabPane) rootPane.getParent().getParent();
        final SingleSelectionModel<Tab> singleSelectionModel = tabPane.getSelectionModel();
        singleSelectionModel.select(2);
        final Tab selectedItem = singleSelectionModel.getSelectedItem();
        selectedItem.setDisable(false);
    }

    @Override
    public void init() {
    }

}
