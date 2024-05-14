package net.gameoflife.objetos.individuos;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;

public class AvanzadoIndividuo extends Individuo{
    public AvanzadoIndividuo(IndividuoConfiguracion individuoConfiguracion) {
        super(TipoIndividuo.AVANZADO,individuoConfiguracion);
    }
    @Override
    public void move() {

    }
}
