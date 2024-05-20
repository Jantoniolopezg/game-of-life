package net.gameoflife.objetos.casilla;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.gameoflife.eventos.Evento;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.recursos.Recurso;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class Casilla {
    //private Point2D<Integer> boardPosition;
    private List<Evento> eventos;
    private List<Individuo> individuos;
    private List<Recurso> recursos;

    @Override
    public String toString(){
        final String dataIndividuos = individuos.stream()
                .map(individuo -> individuo.toString() + "\n")
                .collect(Collectors.joining());
        final String dataRecursos = recursos.stream()
                .map(recurso -> recurso.toString() + "\n")
                .collect(Collectors.joining());
        return dataIndividuos + dataRecursos;
    }
}
