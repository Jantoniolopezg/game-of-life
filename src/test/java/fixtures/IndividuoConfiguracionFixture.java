package fixtures;

import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;

import java.math.BigDecimal;

public class IndividuoConfiguracionFixture {

    private final Integer individualLife;
    private final BigDecimal reproductionProbability;
    private final BigDecimal cloningProbability;

    private IndividuoConfiguracionFixture(Integer individualLife, BigDecimal reproductionProbability, BigDecimal cloningProbability) {
        this.individualLife = individualLife;
        this.reproductionProbability = reproductionProbability;
        this.cloningProbability = cloningProbability;
    }

    public static class IndividuoConfiguracionFixtureBuilder {
        private Integer individualLife;
        private BigDecimal reproductionProbability;
        private BigDecimal cloningProbability;

        public IndividuoConfiguracionFixtureBuilder individualLife(Integer individualLife) {
            this.individualLife = individualLife;
            return this;
        }

        public IndividuoConfiguracionFixtureBuilder reproductionProbability(BigDecimal reproductionProbability) {
            this.reproductionProbability = reproductionProbability;
            return this;
        }

        public IndividuoConfiguracionFixtureBuilder cloningProbability(BigDecimal cloningProbability) {
            this.cloningProbability = cloningProbability;
            return this;
        }

        public IndividuoConfiguracion build() {
            IndividuoConfiguracion individualConfiguration = new IndividuoConfiguracion();
            individualConfiguration.setVidaIndividuo(individualLife != null ? individualLife : 3);
            individualConfiguration.setReproduccionProbabilidad(reproductionProbability != null ? reproductionProbability : BigDecimal.valueOf(0.5));
            individualConfiguration.setClonacionProbabilidad(cloningProbability != null ? cloningProbability : BigDecimal.valueOf(0.3));
            return individualConfiguration;
        }
    }

    public static IndividuoConfiguracionFixtureBuilder builder() {
        return new IndividuoConfiguracionFixtureBuilder();
    }

}

