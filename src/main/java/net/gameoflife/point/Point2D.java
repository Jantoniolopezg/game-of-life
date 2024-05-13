package net.gameoflife.point;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Point2D<T> {
    private T x;
    private T y;
}
