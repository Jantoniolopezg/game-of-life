package fixtures;


import net.gameoflife.objetos.casilla.Casilla;

import java.util.ArrayList;

public class TableroFixture {

    private TableroFixture() {
    }

    public static class TableroFixtureBuilder {
        private Integer width;
        private Integer height;

        public TableroFixtureBuilder width(int width) {
            this.width = width;
            return this;
        }

        public TableroFixtureBuilder height(int height) {
            this.height = height;
            return this;
        }

        public Casilla[][] build() {
            this.height = this.height != null ? this.height : 10;
            this.width = this.width != null ? this.width : 10;
            Casilla[][] cells = new Casilla[width][height];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    cells[x][y] = Casilla.builder()
                            .individuos(new ArrayList<>())
                            .recursos(new ArrayList<>())
                            .build();
                }
            }
            return cells;
        }
    }

    public static TableroFixture.TableroFixtureBuilder builder() {
        return new TableroFixture.TableroFixtureBuilder();
    }
}

