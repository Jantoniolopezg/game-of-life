package net.gameoflife.appconfig;

import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ResourceBundle;

/**
 * Configuración de beans para la integración de JavaFX con Spring.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    /**
     * Es el cargador de ficheros FXML
     */
    @Bean
    public SpringFXMLLoader springFXMLLoader(){return new SpringFXMLLoader(resourceBundle());}

    /**
     * El StageManager se encarga de la navegación y paso de datos entre distintas ventanas.
     * Es parte del BaseController, se puede acceder a el desde cualquier controlador mediante el getter
     * getSceneManager.
     * Mediante Lazy nos aseguramos de que se cree únicamente después de que el contexto de Spring haya arrancado.
     */
    @Bean
    @Lazy
    public StageManager stageManager(Stage stage){return new StageManager(stage,springFXMLLoader());}

    /**
     * El fichero game-of-life.properties contiene las traducciones.
     * En Java, esto se llama ResourceBundles, esto crea el objeto resourceBundle que necesita el FxmlLoader de JavaFX.
     */
    @Bean
    public ResourceBundle resourceBundle(){return ResourceBundle.getBundle("game-of-life");}
}
