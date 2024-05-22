package net.gameoflife.eventos;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.point.Point2D;

import java.util.UUID;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class IndividuoEvento extends Evento{

    @NonNull
    private UUID uuid;

    @NonNull
    private TipoIndividuo tipoIndividuo;

    @NonNull
    private Point2D<Integer> posicion;
}
