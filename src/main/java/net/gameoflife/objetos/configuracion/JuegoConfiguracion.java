package net.gameoflife.objetos.configuracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.gameoflife.point.Point2D;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuegoConfiguracion implements Serializable {

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Point2D<Integer> size;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal factorMejoraPoblacion;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxRecursosPorCasilla;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer maxIndividuosPorCasilla;

}
