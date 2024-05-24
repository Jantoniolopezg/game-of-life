package net.gameoflife.eventos;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import net.gameoflife.point.Point2D;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class IndividuoMueveEvento extends IndividuoEvento {
    @NonNull
    private Point2D<Integer> newPosicion;
    @NonNull
    private Point2D<Double> direccion;

}
