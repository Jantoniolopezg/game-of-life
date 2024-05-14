package net.gameoflife.enumeraciones;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.Objects;

public enum TipoIndividuo {
    NINGUNO("empty-resource.png"),
    BASICO("basic-individual.png"),
    NORMAL("normal-individual.png"),
    AVANZADO("advanced-individual.png");

    @Getter
    private final Image image;

    TipoIndividuo(String resourceName) {
        this.image = new Image(Objects.requireNonNull(getClass().getResource(resourceName)).toExternalForm());
    }
}
