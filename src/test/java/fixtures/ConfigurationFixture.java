package fixtures;

import net.gameoflife.objetos.configuracion.Configuracion;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;

public class ConfigurationFixture {

    public static class ConfigurationFixtureBuilder {
        private JuegoConfiguracion juegoConfiguracion;
        private IndividuoConfiguracion individuoConfiguracion;
        private RecursoConfiguracion recursoConfiguracion;

        public ConfigurationFixtureBuilder juegoConfiguracion(JuegoConfiguracion juegoConfiguracion) {
            this.juegoConfiguracion = juegoConfiguracion;
            return this;
        }

        public ConfigurationFixtureBuilder individuoConfiguracion(IndividuoConfiguracion individuoConfiguracion) {
            this.individuoConfiguracion = individuoConfiguracion;
            return this;
        }

        public ConfigurationFixtureBuilder recursoConfiguracion(RecursoConfiguracion recursoConfiguracion) {
            this.recursoConfiguracion = recursoConfiguracion;
            return this;
        }

        public Configuracion build() {
            Configuracion configuracion = new Configuracion();
            configuracion.setRecursoConfiguracion(recursoConfiguracion != null ? recursoConfiguracion : RecursoConfiguracion.builder().build());
            configuracion.setIndividuoConfiguracion(individuoConfiguracion != null ? individuoConfiguracion : IndividuoConfiguracionFixture.builder().build());
            configuracion.setJuegoConfiguracion(juegoConfiguracion != null ? juegoConfiguracion : JuegoConfiguracionFixture.builder().build());
            return configuracion;
        }
    }

    public static ConfigurationFixtureBuilder builder() {
        return new ConfigurationFixtureBuilder();
    }
}
