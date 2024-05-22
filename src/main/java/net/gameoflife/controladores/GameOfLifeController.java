package net.gameoflife.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.RequiredArgsConstructor;
import net.gameoflife.objetos.fxml.UserData;
import net.gameoflife.servicios.SimulacionServicio;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GameOfLifeController extends BaseController{

    private final SimulationController simulationController;
    private final SimulacionServicio simulacionServicio;
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab simulationTab;

    @FXML
    private Tab mainMenuTab;

    private void onSelectionChangedSimulation(){
        if (mainMenuTab.isSelected()){
            simulationTab.setDisable(true);
        }
        if (simulationTab.isSelected()){
            mainMenuTab.setDisable(true);
            final UserData userData = (UserData) getStageManager().getPrimarySatge().getUserData();
            simulacionServicio.initGame(userData);
            simulationController.drawBoard();
        }
    }

    @Override
    public void init() {
        tabPane.getSelectionModel().select(mainMenuTab);
    }

}