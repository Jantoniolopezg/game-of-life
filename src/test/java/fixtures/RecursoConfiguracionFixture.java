package fixtures;

import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

import java.math.BigDecimal;

public class RecursoConfiguracionFixture {

    private final BigDecimal treasureReproductionImprovementFactor;
    private final BigDecimal libraryCloningImprovementFactor;
    private final Integer resourcesLife;
    private final BigDecimal waterResourceProbability;
    private final BigDecimal foodResourceProbability;
    private final BigDecimal mountainResourceProbability;
    private final BigDecimal treasureResourceProbability;
    private final BigDecimal libraryResourceProbability;
    private final BigDecimal dwellResourceProbability;

    private RecursoConfiguracionFixture(BigDecimal treasureReproductionImprovementFactor,
                                         BigDecimal libraryCloningImprovementFactor,
                                         Integer resourcesLife,
                                         BigDecimal waterResourceProbability,
                                         BigDecimal foodResourceProbability,
                                         BigDecimal mountainResourceProbability,
                                         BigDecimal treasureResourceProbability,
                                         BigDecimal libraryResourceProbability,
                                         BigDecimal dwellResourceProbability) {
        this.treasureReproductionImprovementFactor = treasureReproductionImprovementFactor;
        this.libraryCloningImprovementFactor = libraryCloningImprovementFactor;
        this.resourcesLife = resourcesLife;
        this.waterResourceProbability = waterResourceProbability;
        this.foodResourceProbability = foodResourceProbability;
        this.mountainResourceProbability = mountainResourceProbability;
        this.treasureResourceProbability = treasureResourceProbability;
        this.libraryResourceProbability = libraryResourceProbability;
        this.dwellResourceProbability = dwellResourceProbability;
    }

    public static class RecursoConfiguracionFixtureBuilder {
        private BigDecimal treasureReproductionImprovementFactor;
        private BigDecimal libraryCloningImprovementFactor;
        private Integer resourcesLife;
        private BigDecimal waterResourceProbability;
        private BigDecimal foodResourceProbability;
        private BigDecimal mountainResourceProbability;
        private BigDecimal treasureResourceProbability;
        private BigDecimal libraryResourceProbability;
        private BigDecimal dwellResourceProbability;

        public RecursoConfiguracionFixtureBuilder treasureReproductionImprovementFactor(BigDecimal treasureReproductionImprovementFactor) {
            this.treasureReproductionImprovementFactor = treasureReproductionImprovementFactor;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder libraryCloningImprovementFactor(BigDecimal libraryCloningImprovementFactor) {
            this.libraryCloningImprovementFactor = libraryCloningImprovementFactor;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder resourcesLife(Integer resourcesLife) {
            this.resourcesLife = resourcesLife;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder waterResourceProbability(BigDecimal waterResourceProbability) {
            this.waterResourceProbability = waterResourceProbability;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder foodResourceProbability(BigDecimal foodResourceProbability) {
            this.foodResourceProbability = foodResourceProbability;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder mountainResourceProbability(BigDecimal mountainResourceProbability) {
            this.mountainResourceProbability = mountainResourceProbability;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder treasureResourceProbability(BigDecimal treasureResourceProbability) {
            this.treasureResourceProbability = treasureResourceProbability;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder libraryResourceProbability(BigDecimal libraryResourceProbability) {
            this.libraryResourceProbability = libraryResourceProbability;
            return this;
        }

        public RecursoConfiguracionFixtureBuilder dwellResourceProbability(BigDecimal dwellResourceProbability) {
            this.dwellResourceProbability = dwellResourceProbability;
            return this;
        }

        public RecursoConfiguracion build() {
            RecursoConfiguracion resourceConfiguration = new RecursoConfiguracion();
            resourceConfiguration.setVidaRecursos(resourcesLife != null ? resourcesLife : 5);
            resourceConfiguration.setPozoProbabilidadRecurso(dwellResourceProbability != null ? dwellResourceProbability : BigDecimal.valueOf(0.1));
            resourceConfiguration.setComidaProbabilidadRecurso(foodResourceProbability != null ? foodResourceProbability : BigDecimal.valueOf(0.5));
            resourceConfiguration.setMonteProbabilidadRecurso(mountainResourceProbability != null ? mountainResourceProbability : BigDecimal.valueOf(0.3));
            resourceConfiguration.setAguaProbabilidadRecurso(waterResourceProbability != null ? waterResourceProbability : BigDecimal.valueOf(0.4));
            resourceConfiguration.setBibliotecaProbabilidadRecurso(libraryResourceProbability != null ? libraryResourceProbability : BigDecimal.valueOf(0.3));
            resourceConfiguration.setTesoroProbabilidadRecurso(treasureResourceProbability != null ? treasureResourceProbability : BigDecimal.valueOf(0.25));
            resourceConfiguration.setTesoroFactorMejoraReproduccion(treasureReproductionImprovementFactor != null ? treasureReproductionImprovementFactor : BigDecimal.valueOf(0.35));
            resourceConfiguration.setBibliotecaFactorMejoraClonacion(libraryCloningImprovementFactor != null ? libraryCloningImprovementFactor : BigDecimal.valueOf(0.45));
            return resourceConfiguration;
        }
    }

    public static RecursoConfiguracionFixtureBuilder builder() {
        return new RecursoConfiguracionFixtureBuilder();
    }
}

