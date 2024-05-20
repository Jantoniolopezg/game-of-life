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

    private UUID uuid;
    private TipoIndividuo tipoIndividuo;
    private Long generacion;
    private Integer vida;
    private BigDecimal probabilidadReproduccion;
    private BigDecimal probabilidadClonacion;
    private BigDecimal probabilidadMuerte;
    private Point2D<Double> direccion;
    private Point2D<Double> destinoPosicion;


    public Individuo(TipoIndividuo tipoIndividuo,Long generacion ,IndividuoConfiguracion individuoConfiguracion){
        this.uuid = UUID.randomUUID();
        this.generacion = generacion;
        this.tipoIndividuo = tipoIndividuo;
        this.direccion = new Point2D<>(0.0,0.0);
        this.destinoPosicion = new Point2D<>(0.0,0.0);
        this.vida = individuoConfiguracion.getVidaIndividuo();
        this.probabilidadClonacion = individuoConfiguracion.getClonacionProbabilidad();
        this.probabilidadReproduccion = individuoConfiguracion.getReproduccionProbabilidad();
        this.probabilidadMuerte = BigDecimal.valueOf(BigDecimal.ONE.doubleValue() - this.probabilidadReproduccion.doubleValue());
    }


    @Override
    public String toString(){
        return tipoIndividuo.getLabel() + ": " + uuid +
                "\nGen: " + generacion + "," +
                "Vida: " + vida + "," +
                "Dir: " + direccion + "," +
                "Destino: " + destinoPosicion +
                "\nRepr:" + probabilidadReproduccion +
                "Clon:" + probabilidadClonacion +
                "Muerte:" + probabilidadMuerte + "\n";
    }
}
