package net.gameoflife.objetos.individuos;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;

public class NormalIndividuo extends Individuo{
    public NormalIndividuo(Long generacion, IndividuoConfiguracion individuoConfiguracion) {
        super(TipoIndividuo.NORMAL, generacion, individuoConfiguracion);
    }
}
