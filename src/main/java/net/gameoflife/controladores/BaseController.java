package net.gameoflife.controladores;

import javafx.fxml.Initializable;
import lombok.Getter;
import net.gameoflife.appconfig.ApplicationContextHolder;
import net.gameoflife.appconfig.StageManager;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {

    @Getter
    private StageManager stageManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        stageManager =(StageManager) ApplicationContextHolder.getContext().getBean("stageManager");
        init();
    }

    public abstract void init();

}
