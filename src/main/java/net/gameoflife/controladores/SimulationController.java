package net.gameoflife.controladores;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.gameoflife.enumeraciones.FXMLDefinition;
import net.gameoflife.enumeraciones.TipoIndividuo;
import net.gameoflife.enumeraciones.TipoRecurso;
import net.gameoflife.objetos.casilla.Casilla;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.individuos.Individuo;
import net.gameoflife.objetos.juego.CondicionesFinalizado;
import net.gameoflife.objetos.recursos.Recurso;
import net.gameoflife.point.Point2D;
import net.gameoflife.servicios.CasillaServicio;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.servicios.PersistenciaJuegoServicio;
import net.gameoflife.servicios.SimulacionServicio;
import net.gameoflife.utils.AlertUtil;
import org.springframework.stereotype.Controller;

import java.util.*;

import static net.gameoflife.Constantes.Constantes.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Controller
@RequiredArgsConstructor
public class SimulationController extends BaseController{

    private final Map<TipoIndividuo, Image> individuoImagenes = new HashMap<>();
    private final Map<TipoRecurso, Image> recursoImagenes = new HashMap<>();

    private final CasillaServicio casillaServicio;

    private final SimulacionServicio simulacionServicio;

    private final ConfiguracionServicio configuracionServicio;

    private final PersistenciaJuegoServicio persistenciaJuegoServicio;

    @FXML
    private ToggleButton activateSimulationButton;

    @FXML
    private Button stepByStepButton;

    @FXML
    private ComboBox<String> addElementsCombo;

    @FXML
    private TextArea cellInfoArea;

    @FXML
    @Getter
    private Canvas gameCanvas;

    @FXML
    @Getter
    private Label generationLabel;

    @FXML
    @Getter
    private Label coordinatesLabel;

    private Timeline timeline;

    private final LongProperty generation = new SimpleLongProperty(0L);

    private final BooleanProperty runningProperty = new SimpleBooleanProperty(false);


    @FXML
    void onSaveGameButtonAction(ActionEvent actionEvent){
        persistenciaJuegoServicio.save(simulacionServicio.getTablero());
    }

    @FXML
    void onRemoveResourcesButtonAction(ActionEvent actionEvent) {
        final JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();
        final Casilla[][] tablero = simulacionServicio.getTablero();
        for (int y = 0; y < juegoConfiguracion.getSize().getY(); ++y) {
            for (int x = 0; x < juegoConfiguracion.getSize().getX(); ++x) {
                tablero[x][y].setRecursos(new ArrayList<>());
            }
        }
        drawBoard();
    }

    @FXML
    void onRemoveIndividualsButtonAction(ActionEvent actionEvent) {
        final JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();
        final Casilla[][] tablero = simulacionServicio.getTablero();
        for (int y = 0; y < juegoConfiguracion.getSize().getY(); ++y) {
            for (int x = 0; x < juegoConfiguracion.getSize().getX(); ++x) {
                tablero[x][y].setIndividuos(new ArrayList<>());
            }
        }
        drawBoard();
    }

    @FXML
    void onLoadGameButtonAction(ActionEvent actionEvent){
        final Casilla[][] tableroCargado = persistenciaJuegoServicio.load();
        if (tableroCargado != null){
            simulacionServicio.setTablero(tableroCargado);
            drawBoard();
        }
    }

    @FXML
    void onResetButtonAction(ActionEvent actionEvent) {
        final JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();
        final Casilla[][] tablero = simulacionServicio.getTablero();
        for (int y = 0; y < juegoConfiguracion.getSize().getY(); ++y) {
            for (int x = 0; x < juegoConfiguracion.getSize().getX(); ++x) {
                tablero[x][y] = Casilla.builder().recursos(new ArrayList<>()).individuos(new ArrayList<>()).build();
            }
        }
        drawBoard();
    }

    @FXML
    void onStepByStepButtonAction(ActionEvent actionEvent){
        runGameLoop();
    }
    @FXML
    @Override
    public void init() {
        //initImages(); Esto daba error
        initTimeLine();
        clearBoard(configuracionServicio.getConfiguracion().getJuegoConfiguracion().getSize());
        initCombo();
        initActivateSimulationButton();
        generation.set(0L);
        generationLabel.textProperty().bind(generation.asString());
    }

    @FXML
    void onMouseClickedCanvas(MouseEvent event){
        final String mouseButtonName = event.getButton().name();
        final boolean isPrimary = PRIMARY.equals(mouseButtonName);
        final boolean isSecondary = SECONDARY.equals(mouseButtonName);
        final Point2D<Double> mouseCoordinates = new Point2D<>(event.getX(),event.getY());
        final Point2D<Double> canvasSize = new Point2D<>(gameCanvas.getWidth(), gameCanvas.getHeight());
        casillaServicio.getCasillaDePosicionEnPantalla(simulacionServicio.getTablero(), mouseCoordinates, canvasSize).ifPresent((casilla) ->{
            if (isPrimary){
                simulacionServicio.addElemento(generation.getValue(), casilla, addElementsCombo.getSelectionModel().getSelectedItem());
                drawBoard();
            } else if (isSecondary) {
                simulacionServicio.removeElemento(generation.getValue(), casilla, addElementsCombo.getSelectionModel().getSelectedItem());
                drawBoard();
            }
        });
    }

    @FXML
    void onMouseMoved(MouseEvent event){
        final Point2D<Double> coordenadasRaton = new Point2D<>((double)event.getX(), (double)event.getY());
        if ((coordenadasRaton.getX() >= 0 && coordenadasRaton.getX() <= gameCanvas.getWidth() - 1) && (coordenadasRaton.getY() >= 0 && coordenadasRaton.getY() <= gameCanvas.getHeight() - 1)) {
            final int redondeadoX = (int) Math.floor(coordenadasRaton.getX() / CELL_WIDTH);
            final int redondeadoY = (int) Math.floor(coordenadasRaton.getY() / CELL_HEIGHT);
            coordinatesLabel.setText("X: " + redondeadoX + " Y: " + redondeadoY);
        }else{
            coordinatesLabel.setText(EMPTY);
        }
        final Point2D<Double> canvasSize = new Point2D<>(gameCanvas.getWidth(), gameCanvas.getHeight());
        casillaServicio.getCasillaDePosicionEnPantalla(simulacionServicio.getTablero(), coordenadasRaton, canvasSize)
                .ifPresentOrElse(casilla -> cellInfoArea.setText(casilla.toString()), () -> cellInfoArea.setText(EMPTY));
    }

    @FXML
    public void onFinishButtonAction(ActionEvent actionEvent){
        timeline.stop();
        getStageManager().switchScene(FXMLDefinition.REPORT);
    }

    /**
     * Dibuja el tablero en el gameCanvas.
     */
    //ESTE SIGUE DANDO ERROR PERO AL MENOS NO INMEDIATAMENTE
    public void drawBoard() {
        final Casilla[][] tablero = simulacionServicio.getTablero();
        final Point2D<Integer> tableroSize = configuracionServicio.getConfiguracion().getJuegoConfiguracion().getSize();
        clearBoard(tableroSize);
        final GraphicsContext graphicsContext2D = gameCanvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.LIGHTBLUE);

        for (int y = 0; y < tableroSize.getY(); y++) {
            for (int x = 0; x < tableroSize.getX(); x++){
                final Casilla casilla = tablero[x][y];
                graphicsContext2D.fillRect((x * CELL_WIDTH) + 1, (y * CELL_HEIGHT) + 1, CELL_WIDTH - 1, CELL_HEIGHT - 1);

                final List<Individuo> individuos = casilla.getIndividuos();
                for (int individuoIndex = 0; individuoIndex < individuos.size(); individuoIndex++) {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResource(individuos.get(individuoIndex).getTipoIndividuo().getImageResourceName()).toExternalForm()));
                    graphicsContext2D.drawImage(image, (x * CELL_WIDTH) + individuoIndex * IMAGE_WIDTH, (y * CELL_HEIGHT));
                }
                final List<Recurso> recursos = casilla.getRecursos();
                for (int recursoIndex = 0; recursoIndex < recursos.size(); recursoIndex++) {
                    final Image image = new Image(Objects.requireNonNull(getClass().getResource(recursos.get(recursoIndex).getTipoRecurso().getImageResourceName()).toExternalForm()));
                    graphicsContext2D.drawImage(image, (x * CELL_WIDTH) + recursoIndex * IMAGE_WIDTH, (y * CELL_HEIGHT) + IMAGE_HEIGHT);
                }
            }
        }
    }
    /* Asi como esta da error
    public void drawBoard() {
        final Casilla[][] tablero = simulacionServicio.getTablero();
        final Point2D<Integer> tableroSize = configuracionServicio.getConfiguracion().getJuegoConfiguracion().getSize();
        clearBoard(tableroSize);
        final GraphicsContext graphicsContext2D = gameCanvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.LIGHTBLUE);

        for (int y = 0; y < tableroSize.getY(); y++) {
            for (int x = 0; x < tableroSize.getX(); x++){
                final Casilla casilla = tablero[x][y];
                graphicsContext2D.fillRect((x * CELL_WIDTH) + 1, (y * CELL_HEIGHT) + 1, CELL_WIDTH - 1, CELL_HEIGHT - 1);

                final List<Individuo> individuos = casilla.getIndividuos();
                for (int individuoIndex = 0; individuoIndex < individuos.size(); individuoIndex++) {
                    graphicsContext2D.drawImage(individuoImagenes.get(individuos.get(individuoIndex).getTipoIndividuo()), (x * CELL_WIDTH) + individuoIndex * IMAGE_WIDTH, (y * CELL_HEIGHT));
                }
                final List<Recurso> recursos = casilla.getRecursos();
                for (int recursoIndex = 0; recursoIndex < recursos.size(); recursoIndex++) {
                    graphicsContext2D.drawImage(recursoImagenes.get(recursos.get(recursoIndex).getTipoRecurso()), (x * CELL_WIDTH) + recursoIndex * IMAGE_WIDTH, (y * CELL_HEIGHT) + IMAGE_HEIGHT);
                }
            }
        }
    }

     */

    /*




    AQUI ABAJO ESTA EL PROBLEMA, TIENE QUE VER CON EL COMO SE CARGAN LAS IMAGENES




     */
    //Basicamete lo que antes haciamos cad vez que dibujabamos el mapa ahora lo hacemos una vez y lo guardamos
    private void initImages() {
        Arrays.stream(TipoIndividuo.values()).forEach(tipoIndividuo -> {
            final Image image = new Image(Objects.requireNonNull(getClass().getResource(tipoIndividuo.getImageResourceName()).toExternalForm()));
            individuoImagenes.put(tipoIndividuo, image);
        });
        Arrays.stream(TipoRecurso.values()).forEach(tipoRecurso -> {
            final Image image = new Image(Objects.requireNonNull(getClass().getResource(tipoRecurso.getImageResourceName()).toExternalForm()));
            recursoImagenes.put(tipoRecurso, image);
        });
    }


    private void initTimeLine() {
        final KeyFrame keyFrame = new KeyFrame(Duration.millis(SIMULATION_SPEED), actionEvent -> {
            runGameLoop();
        });
        this.timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        runningProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue){
                timeline.play();
            }else {
                timeline.stop();
            }
        });
    }

    private void clearBoard(Point2D<Integer> size) {
        gameCanvas.setWidth(size.getX() * CELL_WIDTH);
        gameCanvas.setHeight(size.getY() * CELL_HEIGHT);
        final GraphicsContext graphicsContext2D = gameCanvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.WHITE);
        graphicsContext2D.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    private void initCombo() {
        Arrays.stream(TipoIndividuo.values())
                .filter(tipoIndividuo -> tipoIndividuo != TipoIndividuo.NINGUNO)
                .forEach(tipoIndividuo -> addElementsCombo.getItems().add(INDIVIDUAL + tipoIndividuo.name()));
        Arrays.stream(TipoRecurso.values())
                .filter(tipoRecurso -> tipoRecurso != TipoRecurso.VACIO)
                .forEach(tipoRecurso -> addElementsCombo.getItems().add(RESOURCE + tipoRecurso.name()));
        addElementsCombo.disableProperty().bind(runningProperty);
        stepByStepButton.disableProperty().bind(runningProperty);
    }

    private void initActivateSimulationButton() {
        activateSimulationButton.setOnAction((actionEvent) -> runningProperty.set(!runningProperty.get()));
        activateSimulationButton.textProperty().bind(
                Bindings.when(runningProperty)
                        .then("Parar simulacion")
                        .otherwise("Activar simulacion"));
    }

    /**
     * Este es el bucle principal del juego, manejado por el Timeline.
     */
    private void runGameLoop() {
        simulacionServicio.runGameLoop(generation.get());
        Platform.runLater(this::drawBoard);
        final CondicionesFinalizado condicionesFinalizado = simulacionServicio.getCondicionesFinalizado(generation.get());
        if (condicionesFinalizado.isTerminado()) {
            timeline.stop();
            runningProperty.set(false);
            Platform.runLater(() -> finishGame(condicionesFinalizado));
        } else {
            generation.set(generation.get() + 1);
        }
    }

    private void finishGame(CondicionesFinalizado condicionesFinalizado) {
        final String alertMessage = condicionesFinalizado.isTodosMuertos()
                ? "No ha habido supervivientes"
                : "El individuo " + condicionesFinalizado.getIndividuo().getUuid() + "es el ganador.";
        AlertUtil.showInfo("Game over\n" + alertMessage);
        getStageManager().switchScene(FXMLDefinition.REPORT);
    }
}
