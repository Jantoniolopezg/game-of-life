package net.gameoflife.enumeraciones;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.Objects;

public enum TipoRecurso {
    VACIO("empty-resource.png"),
    AGUA("water-resource.png"),
    COMIDA("food-resource.png"),
    MONTE("mountain-resource.png"),
    TESORO("treasure-resource.png"),
    BIBLIOTECA("library-resource.png"),
    POZO("dwell-resource.png");

    @Getter
    private final Image image;

    TipoRecurso(String resourceName) {
        this.image = new Image(Objects.requireNonNull(getClass().getResource(resourceName)).toExternalForm());
    }
}
