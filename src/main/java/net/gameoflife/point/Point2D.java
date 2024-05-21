package net.gameoflife.point;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point2D<T> implements Serializable {
    private T x;
    private T y;

    @Override
    public String toString(){return "X: " + x + ", Y: " + y;}
}
