package net.gameoflife.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import net.gameoflife.enumeraciones.FXMLDefinition;
import net.gameoflife.servicios.EventoServicio;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ReportController extends BaseController{

    private final EventoServicio eventoServicio;

    @FXML
    private Button backMainMenuButton;

    @FXML
    private CheckBox clonesCheck;

    @FXML
    private CheckBox individualOperationsCheck;

    @FXML
    private ComboBox<UUID> individualSelectionCombo;

    @FXML
    private CheckBox individualTreeCheck;

    @FXML
    private CheckBox longevityCheck;

    @FXML
    private CheckBox maxLivedCheck;

    @FXML
    private CheckBox maxWaterCheck;

    @FXML
    private VBox reportTextArea;

    @FXML
    private CheckBox reproductionsCheck;

    @Override
    public void init() {
        initCombo();

    }

    @FXML
    void onBackMainMenuAction(ActionEvent event) {
        getStageManager().switchScene(FXMLDefinition.GAME_OF_LIFE);
    }

    private void initCombo(){

    }
}
