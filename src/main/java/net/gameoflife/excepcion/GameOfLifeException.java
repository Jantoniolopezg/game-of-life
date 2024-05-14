package net.gameoflife.excepcion;

public class GameOfLifeException extends RuntimeException{
    public GameOfLifeException(String message){
        super(message);
    }

    public GameOfLifeException(String message, Throwable throwable){
        super(message,throwable);
    }
}
