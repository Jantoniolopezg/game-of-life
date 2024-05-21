package net.gameoflife.objetos.configuracion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndividuoConfiguracion implements Serializable {

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer vidaIndividuo;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal reproduccionProbabilidad;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal clonacionProbabilidad;

}
