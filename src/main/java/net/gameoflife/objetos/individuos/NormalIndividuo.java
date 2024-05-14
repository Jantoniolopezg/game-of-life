package net.gameoflife.objetos.individuos;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;

public class NormalIndividuo extends Individuo{
    public NormalIndividuo(IndividuoConfiguracion individuoConfiguracion) {
        super(TipoIndividuo.NORMAL,individuoConfiguracion);
    }
    @Override
    public void move() {

    }
}
