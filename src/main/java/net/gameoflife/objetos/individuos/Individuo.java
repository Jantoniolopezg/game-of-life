package net.gameoflife.objetos.individuos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.point.Point2D;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class Individuo {

    private final UUID uuid;
    private final TipoIndividuo tipoIndividuo;
    private Point2D<Integer> posicion;
    private Integer generacion;
    private Integer vida;
    private BigDecimal probabilidadReproduccion;
    private BigDecimal probabilidadClonacion;
    private BigDecimal probabilidadMuerte;


    public Individuo(TipoIndividuo tipoIndividuo, IndividuoConfiguracion individuoConfiguracion){
        this.uuid = UUID.randomUUID();
        this.tipoIndividuo = tipoIndividuo;
        this.probabilidadClonacion = individuoConfiguracion.getClonacionProbabilidad();
        this.probabilidadReproduccion = individuoConfiguracion.getReproduccionProbabilidad();
        this.vida = individuoConfiguracion.getVidaIndividuo();
        this.probabilidadMuerte = BigDecimal.valueOf(1).subtract(this.probabilidadReproduccion);
    }

    public abstract void move();

    @Override
    public String toString(){
        return "Individuo:\n" +
                "uuid=" + uuid +
                "\ntipoIndividuo=" + tipoIndividuo +
                "\nposicion=" + posicion +
                "\ngeneracion=" + generacion +
                "\nvida=" + vida +
                "\nprobabilidadReproduccion=" + probabilidadReproduccion +
                "\nprobabilidadClonacion=" + probabilidadClonacion +
                "\nprobabilidadMuerte" + probabilidadMuerte + "\n";
    }
}
