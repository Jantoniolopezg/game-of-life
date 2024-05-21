package net.gameoflife.eventos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoEvento;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class Evento implements Serializable {
    private TipoEvento tipoEvento;
}
