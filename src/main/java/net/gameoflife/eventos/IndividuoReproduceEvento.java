package net.gameoflife.eventos;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.gameoflife.enumeraciones.TipoIndividuo;

import java.util.UUID;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class IndividuoReproduceEvento extends IndividuoEvento{
    @NonNull
    private UUID parejaUUID;
    @NonNull
    private TipoIndividuo parejaTipo;
    @NonNull
    private UUID hijoUUID;
    @NonNull
    private TipoIndividuo hijoTipo;
}
