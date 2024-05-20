package net.gameoflife.controladores;

import javafx.fxml.FXML;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.awt.*;

public class ErrorController extends BaseController{

    @FXML
    private TextArea errorMessageTextArea;
    @Override
    public void init() {
        final Throwable throwable = (Throwable) getStageManager().getPrimarySatge().getUserData();
        final String errorMessage = ExceptionUtils.getStackTrace(throwable);
        errorMessageTextArea.setText(errorMessage);
    }
}
