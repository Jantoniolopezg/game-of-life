package net.gameoflife.servicios;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.individuos.Individuo;

public interface IndividuoServicio {
    Individuo getRandomIndividuo(Long generacion, IndividuoConfiguracion individuoConfiguracion);

    Individuo getIndividuo(Long generacion, TipoIndividuo tipoIndividuo, IndividuoConfiguracion individuoConfiguracion);

    TipoIndividuo getTipoIndividuoDeComboLabel(String label);

    void reproducir(long generacion, Casilla casilla);

    void clonacion(long generacion, Casilla casilla);

    void senicidio(Casilla casilla);

    void actualizarVidaIndividuos(long generacion, Casilla casilla);

    void eliminarIndividuo(Casilla casilla, Individuo individuo);
}
