package net.gameoflife;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.gameoflife.appconfig.StageManager;
import net.gameoflife.enumeraciones.FXMLDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GameOfLifeApplication extends Application {

    private StageManager stageManager;
    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        Application.launch(GameOfLifeApplication.class, args);
    }

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(GameOfLifeApplication.class);
        String[] args = getParameters().getRaw().toArray(new String[0]);
        springContext = builder.run(args);
    }

    @Override
    public void start(Stage stage) {
        stageManager = springContext.getBean(StageManager.class, stage);
        setExceptionHandler();
        stageManager.switchScene(FXMLDefinition.GAME_OF_LIFE);
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    /**
     * Manejador de errores global, en caso de que haya una excepción nos redirecciona a la página de error.
     */
    private void setExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
            stageManager.getPrimarySatge().setUserData(throwable);
            stageManager.switchScene(FXMLDefinition.ERROR);
        });
    }

}