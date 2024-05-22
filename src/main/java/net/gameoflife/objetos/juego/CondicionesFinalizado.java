package net.gameoflife.objetos.juego;

import lombok.Builder;
import lombok.Getter;
import net.gameoflife.objetos.individuos.Individuo;

@Getter
@Builder
public class CondicionesFinalizado {
    private Individuo individuo;
    private boolean todosMuertos;
    private boolean terminado;
}
