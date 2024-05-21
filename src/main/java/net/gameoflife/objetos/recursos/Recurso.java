package net.gameoflife.objetos.recursos;

import lombok.Getter;
import lombok.Setter;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class Recurso implements Serializable {

    private UUID uuid;
    private TipoRecurso tipoRecurso;
    private RecursoConfiguracion recursoConfiguracion;
    private Long generacion;
    private Integer vidaRecurso;


    public Recurso(TipoRecurso tipoRecurso,Long generacion, RecursoConfiguracion recursoConfiguracion) {
        this.uuid = UUID.randomUUID();
        this.tipoRecurso = tipoRecurso;
        this.generacion = generacion;
        this.vidaRecurso = recursoConfiguracion.getVidaRecursos();
        this.recursoConfiguracion = recursoConfiguracion;
    }

    @Override
    public String toString(){
        return tipoRecurso.getLabel() + ": " + uuid +
                "\nGen: " + generacion + "," +
                "Vida: " + vidaRecurso + "\n";
    }
}
