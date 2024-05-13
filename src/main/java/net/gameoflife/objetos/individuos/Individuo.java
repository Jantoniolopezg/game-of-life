package net.gameoflife.objetos.individuos;

import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.point.Point2D;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public abstract class Individuo {

    private final UUID uuid;
    private final TipoIndividuo tipoIndividuo;
    private Point2D<Integer> posicion;
    private Integer generacion;
    private Integer vida;
    private BigDecimal probabilidadReproduccion;
    private BigDecimal probabilidadClonacion;
    private BigDecimal probabilidadMuerte;


    public Individuo(TipoIndividuo tipoIndividuo){
        this.uuid = UUID.randomUUID();
        this.tipoIndividuo = tipoIndividuo;
    }

    public abstract void move();
}
