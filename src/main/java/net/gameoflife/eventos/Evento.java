package net.gameoflife.eventos;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.gameoflife.enumeraciones.TipoEvento;

import java.io.Serializable;

@Getter
@SuperBuilder
@RequiredArgsConstructor
@ToString
public class Evento implements Serializable {
    @NonNull
    private final Long generation;
    @NonNull
    private TipoEvento tipoEvento;
}
