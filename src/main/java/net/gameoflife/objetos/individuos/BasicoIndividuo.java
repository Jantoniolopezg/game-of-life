package net.gameoflife.objetos.individuos;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;

public class BasicoIndividuo extends Individuo{
    public BasicoIndividuo(Long generacion, IndividuoConfiguracion individuoConfiguracion) {
        super(TipoIndividuo.BASICO, generacion, individuoConfiguracion);
    }
}
