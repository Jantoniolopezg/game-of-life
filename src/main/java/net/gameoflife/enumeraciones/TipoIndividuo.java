package net.gameoflife.enumeraciones;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.Objects;

public enum TipoIndividuo {
    AVANZADO("advanced-individual.png","Avanzado"),

    NORMAL("normal-individual.png","Normal"),

    BASICO("basic-individual.png","Basico"),
    NINGUNO("empty-resource.png","Ninguno");

    @Getter
    private final String imageResourceName;

    @Getter
    private final String label;

    TipoIndividuo(String imageResourceName, String label) {
        this.label = label;
        this.imageResourceName = imageResourceName;
    }
}
