package fixtures;

import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.individuos.AvanzadoIndividuo;
import net.gameoflife.objetos.individuos.BasicoIndividuo;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.individuos.NormalIndividuo;
import net.gameoflife.point.Point2D;

import java.math.BigDecimal;
import java.util.UUID;

public class IndividuoFixture {
    private final UUID uuid;
    private final TipoIndividuo tipoIndividuo;
    private final Long generation;
    private final Integer life;
    private final BigDecimal reproductionProbability;
    private final BigDecimal cloningProbability;
    private final BigDecimal deathProbability;
    private final Point2D<Double> direction;
    private final Point2D<Double> targetPosition;

    private IndividuoFixture(TipoIndividuo individualType, Long generation, IndividuoConfiguracion individualConfiguration) {
        this.uuid = UUID.randomUUID();
        this.generation = generation;
        this.tipoIndividuo = individualType;
        this.life = individualConfiguration.getVidaIndividuo();
        this.cloningProbability = individualConfiguration.getClonacionProbabilidad();
        this.reproductionProbability = individualConfiguration.getReproduccionProbabilidad();
        this.deathProbability = BigDecimal.ONE.subtract(this.reproductionProbability);
        this.direction = new Point2D<>(0.0, 0.0);
        this.targetPosition = new Point2D<>(0.0, 0.0);
    }

    public static class IndividuoFixtureBuilder {
        private UUID uuid;
        private TipoIndividuo tipoIndividuo;
        private Long generation;
        private Integer life;
        private BigDecimal reproductionProbability;
        private BigDecimal cloningProbability;
        private BigDecimal deathProbability;
        private Point2D<Double> direction;
        private Point2D<Double> targetPosition;

        private IndividuoConfiguracion individualConfiguration;

        public IndividuoFixtureBuilder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public IndividuoFixtureBuilder tipoIndividuo(TipoIndividuo individualType) {
            this.tipoIndividuo = individualType;
            return this;
        }

        public IndividuoFixtureBuilder generation(Long generation) {
            this.generation = generation;
            return this;
        }

        public IndividuoFixtureBuilder life(Integer life) {
            this.life = life;
            return this;
        }

        public IndividuoFixtureBuilder direction(Point2D<Double> direction) {
            this.direction = direction;
            return this;
        }

        public IndividuoFixtureBuilder targetPosition(Point2D<Double> targetPosition) {
            this.targetPosition = targetPosition;
            return this;
        }

        public IndividuoFixtureBuilder individuoConfiguracion(IndividuoConfiguracion individuoConfiguracion) {
            this.individualConfiguration = individuoConfiguracion;
            return this;
        }

        public IndividuoFixtureBuilder reproductionProbability(BigDecimal reproductionProbability) {
            this.reproductionProbability = reproductionProbability;
            return this;
        }

        public IndividuoFixtureBuilder cloningProbability(BigDecimal cloningProbability) {
            this.cloningProbability = cloningProbability;
            return this;
        }

        public IndividuoFixtureBuilder deathProbability(BigDecimal deathProbability) {
            this.deathProbability = deathProbability;
            return this;
        }

        public Individuo build(TipoIndividuo individualType) {
            Individuo individual = new BasicoIndividuo(0L, individualConfiguration != null ? individualConfiguration : IndividuoConfiguracionFixture.builder().build());
            switch (individualType) {
                case NORMAL -> {
                    individual = new NormalIndividuo(0L, individualConfiguration != null ? individualConfiguration : IndividuoConfiguracionFixture.builder().build());
                }
                case AVANZADO -> {
                    individual = new AvanzadoIndividuo(0L, individualConfiguration != null ? individualConfiguration : IndividuoConfiguracionFixture.builder().build());
                }
            }
            individual.setUuid(uuid != null ? uuid : UUID.fromString("26929514-237c-11ed-861d-0242ac120002"));
            individual.setGeneracion(generation != null ? generation : 1L);
            individual.setVida(life != null ? life : 4);
            individual.setProbabilidadReproduccion(reproductionProbability != null ? reproductionProbability : BigDecimal.valueOf(0.5));
            individual.setProbabilidadClonacion(cloningProbability != null ? cloningProbability : BigDecimal.valueOf(0.4));
            individual.setProbabilidadMuerte(deathProbability != null ? deathProbability : BigDecimal.valueOf(BigDecimal.ONE.doubleValue() - individual.getProbabilidadReproduccion().doubleValue()));
            individual.setDireccion(direction != null ? direction : new Point2D<>(0.0, 0.0));
            individual.setDestinoPosicion(targetPosition != null ? targetPosition : new Point2D<>(0.0, 0.0));
            return individual;
        }
    }

    public static IndividuoFixture.IndividuoFixtureBuilder builder() {
        return new IndividuoFixture.IndividuoFixtureBuilder();
    }
}

