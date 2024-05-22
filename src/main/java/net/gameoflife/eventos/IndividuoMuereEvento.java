package net.gameoflife.eventos;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.gameoflife.enumeraciones.RazonMuerte;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class IndividuoMuereEvento extends IndividuoEvento{

    @NonNull
    private RazonMuerte razonMuerte;
}
