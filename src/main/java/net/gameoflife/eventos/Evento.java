package net.gameoflife.eventos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoEvento;

@Getter
@Setter
@Builder
public class Evento {
    private TipoEvento tipoEvento;
}
