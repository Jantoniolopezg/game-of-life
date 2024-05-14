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
public class Configuracion {

    @JsonProperty("juego")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JuegoConfiguracion juegoConfiguracion;

    @JsonProperty("individuo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private IndividuoConfiguracion individuoConfiguracion;

    @JsonProperty("recurso")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RecursoConfiguracion recursoConfiguracion;

}
