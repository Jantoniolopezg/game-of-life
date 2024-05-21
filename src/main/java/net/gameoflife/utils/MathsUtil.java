package net.gameoflife.utils;

import net.gameoflife.point.Point2D;

public class MathsUtil {

    public static boolean isInRange(double limiteInferior, double limiteSuperior, double numero) {
        return numero >= limiteInferior && numero <= limiteSuperior;
    }

    public static <T extends Number & Comparable<T>> T clamp(T valor, T min, T max){
        return valor.compareTo(min) < 0 ? min : valor.compareTo(max) > 0 ? max : valor;
    }

    public static double distance(Point2D<Double> point1, Point2D<Double> point2) {
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
    }

    public static Point2D<Double> normalize(Point2D<Double> vector) {
        double magnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
        double normalizadoX = vector.getX() / magnitude;
        double normalizadoY = vector.getY() / magnitude;
        return new Point2D<>(normalizadoX, normalizadoY);
    }
}
