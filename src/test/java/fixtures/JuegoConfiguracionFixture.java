package fixtures;

import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.point.Point2D;

import java.math.BigDecimal;

public class JuegoConfiguracionFixture {

    private final Point2D<Integer> size;
    private final BigDecimal populationImprovementFactor;
    private final Integer maxResourcesPerCell;
    private final Integer maxIndividualsPerCell;

    private JuegoConfiguracionFixture(Point2D<Integer> size,
                                     BigDecimal populationImprovementFactor,
                                     Integer maxResourcesPerCell,
                                     Integer maxIndividualsPerCell) {
        this.size = size;
        this.populationImprovementFactor = populationImprovementFactor;
        this.maxResourcesPerCell = maxResourcesPerCell;
        this.maxIndividualsPerCell = maxIndividualsPerCell;
    }

    public static class JuegoConfiguracionFixtureBuilder {
        private Point2D<Integer> size;
        private BigDecimal populationImprovementFactor;
        private Integer maxRecursosPorCasilla;
        private Integer maxIndividuosPorCasilla;

        public JuegoConfiguracionFixtureBuilder size(Point2D<Integer> size) {
            this.size = size;
            return this;
        }

        public JuegoConfiguracionFixtureBuilder populationImprovementFactor(BigDecimal populationImprovementFactor) {
            this.populationImprovementFactor = populationImprovementFactor;
            return this;
        }

        public JuegoConfiguracionFixtureBuilder maxRecursosPorCasilla(Integer maxRecursosPorCasilla) {
            this.maxRecursosPorCasilla = maxRecursosPorCasilla;
            return this;
        }

        public JuegoConfiguracionFixtureBuilder maxIndividuosPorCasilla(Integer maxIndividuosPorCasilla) {
            this.maxIndividuosPorCasilla = maxIndividuosPorCasilla;
            return this;
        }

        public JuegoConfiguracion build() {
            JuegoConfiguracion juegoConfiguracion = new JuegoConfiguracion();
            juegoConfiguracion.setMaxRecursosPorCasilla(maxRecursosPorCasilla != null ? maxRecursosPorCasilla : 5);
            juegoConfiguracion.setMaxIndividuosPorCasilla(maxIndividuosPorCasilla != null ? maxIndividuosPorCasilla : 5);
            juegoConfiguracion.setFactorMejoraPoblacion(populationImprovementFactor != null ? populationImprovementFactor : BigDecimal.valueOf(0.5));
            juegoConfiguracion.setSize(size != null ? size : new Point2D<>(11, 9));
            return juegoConfiguracion;
        }
    }

    public static JuegoConfiguracionFixtureBuilder builder() {
        return new JuegoConfiguracionFixtureBuilder();
    }
}