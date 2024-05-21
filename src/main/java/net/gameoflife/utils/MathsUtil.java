package net.gameoflife.utils;

public class MathsUtil {

    public static boolean isInRange(double limiteInferior, double limiteSuperior, double numero) {
        return numero >= limiteInferior && numero <= limiteSuperior;
    }

    public static <T extends Number & Comparable<T>> T clamp(T valor, T min, T max){
        return valor.compareTo(min) < 0 ? min : valor.compareTo(max) > 0 ? max : valor;
    }
}
