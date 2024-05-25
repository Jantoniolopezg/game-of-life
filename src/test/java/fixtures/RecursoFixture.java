package fixtures;

import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.objetos.recursos.*;

import java.util.UUID;

public class RecursoFixture {
    private final UUID uuid;
    private final TipoRecurso tipoRecurso;
    private final RecursoConfiguracion recursoConfiguracion;
    private final Long generation;
    private final Integer resourceLife;

    private RecursoFixture(UUID uuid, TipoRecurso tipoRecurso, RecursoConfiguracion recursoConfiguracion, Long generation, Integer resourceLife) {
        this.uuid = uuid;
        this.tipoRecurso = tipoRecurso;
        this.recursoConfiguracion = recursoConfiguracion;
        this.generation = generation;
        this.resourceLife = resourceLife;
    }

    public static class RecursoFixtureBuilder {
        private UUID uuid;
        private TipoRecurso tipoRecurso;
        private RecursoConfiguracion recursoConfiguracion;
        private Long generation;
        private Integer resourceLife;

        public RecursoFixtureBuilder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public RecursoFixtureBuilder tipoRecurso(TipoRecurso tipoRecurso) {
            this.tipoRecurso = tipoRecurso;
            return this;
        }

        public RecursoFixtureBuilder recursoConfiguracion(RecursoConfiguracion recursoConfiguracion) {
            this.recursoConfiguracion = recursoConfiguracion;
            return this;
        }

        public RecursoFixtureBuilder generation(Long generation) {
            this.generation = generation;
            return this;
        }

        public RecursoFixtureBuilder resourceLife(Integer resourceLife) {
            this.resourceLife = resourceLife;
            return this;
        }

        public Recurso build(TipoRecurso tipoRecurso) {
            Recurso resource = new AguaRecurso(generation, recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
            switch (tipoRecurso) {
                case COMIDA -> resource = new ComidaRecurso(generation, recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
                case MONTE -> resource = new MonteRecurso(generation, recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
                case TESORO -> resource = new TesoroRecurso(generation, recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
                case BIBLIOTECA -> resource = new BibliotecaRecurso(generation, recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
                case POZO -> resource = new PozoRecurso(generation, recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
            }
            resource.setUuid(uuid != null ? uuid : UUID.fromString("26929514-237c-11ed-861d-0242ac120002"));
            resource.setVidaRecurso(resourceLife != null ? resourceLife : 4);
            resource.setGeneracion(generation != null ? generation : 2L);
            resource.setRecursoConfiguracion(recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracionFixture.builder().build());
            return resource;
        }
    }

    public static RecursoFixtureBuilder builder() {
        return new RecursoFixtureBuilder();
    }
}

