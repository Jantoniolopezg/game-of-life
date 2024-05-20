package net.gameoflife.point;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point2D<T> {
    private T x;
    private T y;

    @Override
    public String toString(){return "X: " + x + ", Y: " + y;}
}
