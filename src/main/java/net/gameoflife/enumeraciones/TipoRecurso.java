package net.gameoflife.enumeraciones;

import lombok.Getter;

public enum TipoRecurso {
    VACIO("empty-resource.png","Vacio"),
    AGUA("water-resource.png","Agua"),
    COMIDA("food-resource.png","Comida"),
    MONTE("mountain-resource.png","Montaña"),
    TESORO("treasure-resource.png","Tesoro"),
    BIBLIOTECA("library-resource.png","Biblioteca"),
    POZO("dwell-resource.png","Pozo");

    @Getter
    private final String imageResourceName;

    @Getter
    private final String label;

    TipoRecurso(String imageResourceName, String label) {
        this.label = label;
        this.imageResourceName = imageResourceName;
    }
}
