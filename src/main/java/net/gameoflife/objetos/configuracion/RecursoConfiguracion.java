package net.gameoflife.objetos.configuracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecursoConfiguracion {

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal tesoroFactorMejoraReproduccion;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal bibliotecaFactorMejoraClonacion;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer vidaRecursos;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal aguaProbabilidadRecurso;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal comidaProbabilidadRecurso;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal monteProbabilidadRecurso;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal tesoroProbabilidadRecurso;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal bibliotecaProbabilidadRecurso;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal pozoProbabilidadRecurso;

}
