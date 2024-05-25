package net.gameoflife.utils;

import net.gameoflife.point.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathsUtilTest {

    @Test
    void isInRange() {
        assertTrue(MathsUtil.isInRange(0.0, 1.0, 0.5));
        assertTrue(MathsUtil.isInRange(0.0, 1.0, 0.00001));
        assertFalse(MathsUtil.isInRange(0.0, 0.01, -0.000001));
    }

    @Test
    void clamp() {
        assertEquals(10, MathsUtil.clamp(12, 0, 10));
        assertEquals(10.0, MathsUtil.clamp(12.0, 0.0, 10.0));
        assertEquals(-100, MathsUtil.clamp(-1234, -100, 100));
        assertEquals(15, MathsUtil.clamp(15, 0, 20));
    }

    @Test
    void normalize() {
        Point2D<Double> vectorUp = new Point2D<>(0.0, -4.0);
        Point2D<Double> normalizedUp = MathsUtil.normalize(vectorUp);
        Point2D<Double> vectorDown = new Point2D<>(0.0, 1.4);
        Point2D<Double> normalizedDown = MathsUtil.normalize(vectorDown);
        Point2D<Double> vectorLeft = new Point2D<>(-4.0, 0.0);
        Point2D<Double> normalizedLeft = MathsUtil.normalize(vectorLeft);
        Point2D<Double> vectorRight = new Point2D<>(3.0, 0.0);
        Point2D<Double> normalizedRight = MathsUtil.normalize(vectorRight);
        assertEquals(0, normalizedUp.getX());
        assertEquals(-1, normalizedUp.getY());
        assertEquals(0, normalizedDown.getX());
        assertEquals(1, normalizedDown.getY());
        assertEquals(-1, normalizedLeft.getX());
        assertEquals(0, normalizedLeft.getY());
        assertEquals(1, normalizedRight.getX());
        assertEquals(0, normalizedRight.getY());
    }

    @Test
    void distance(){
        Point2D<Double> point1 = new Point2D<>(0.0, 1.0);
        Point2D<Double> point2 = new Point2D<>(0.0, 0.0);

        assertEquals(1, MathsUtil.distance(point1,point2));
    }

}