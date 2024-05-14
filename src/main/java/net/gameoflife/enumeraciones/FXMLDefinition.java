package net.gameoflife.enumeraciones;

import javafx.scene.image.Image;
import lombok.Getter;
import net.gameoflife.utils.ResourceBundleUtil;

import java.util.Objects;

//Esto basicamente es para los FXML que usaremos para JavaFX, lo de resizable, modal y maximized
// es para la ventana en si.
public enum FXMLDefinition {
    GAME_OF_LIFE(false, false, false) {
        @Override
        public String getTitle() {
            return "Game of life";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/game-of-life.fxml";
        }

    },
    MAIN_MENU(false, false, false) {
        @Override
        public String getTitle() {
            return "Game of life - " + ResourceBundleUtil.getLabel("screen.main.menu");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/main-menu.fxml";
        }

    },
    CONFIGURATION(false, false, false) {
        @Override
        public String getTitle() {
            return "Game of life - " + ResourceBundleUtil.getLabel("screen.configuration");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/configuration.fxml";
        }

    },
    SIMULATION(false, false, false) {
        @Override
        public String getTitle() {
            return "Game of life - " + ResourceBundleUtil.getLabel("screen.simulation");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/simulation.fxml";
        }

    },
    ERROR(false, false, false) {
        @Override
        public String getTitle() {
            return "Game of life - " + ResourceBundleUtil.getLabel("screen.error");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/error.fxml";
        }

    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    @Getter
    private final boolean modal;

    @Getter
    private final boolean maximized;

    @Getter
    private final boolean resizable;

    FXMLDefinition(boolean resizable, boolean modal, boolean maximized) {
        this.modal = modal;
        this.maximized = maximized;
        this.resizable = resizable;
    }

    public Image getIcon() {
        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("game-of-life.png")));
    }
}
