package net.gameoflife.appconfig;

import javafx.fxml.FXMLLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.excepcion.GameOfLifeException;
import net.gameoflife.objetos.fxml.FXMLLoad;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringFXMLLoader {
    private final ResourceBundle resourceBundle;

    public FXMLLoad load(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(ApplicationContextHolder.getContext()::getBean);
            fxmlLoader.setResources(resourceBundle);
            fxmlLoader.setLocation(getClass().getResource(fxmlPath));
            return FXMLLoad.builder()
                    .parent(fxmlLoader.load())
                    .fxmlLoader(fxmlLoader)
                    .build();
        } catch (IOException ioException){
            log.error("Ha habido un error al cargar el FXML " + fxmlPath, ioException);
            throw new GameOfLifeException("Ha habido un error al cargar el FXML " + fxmlPath);
        }
    }
}
