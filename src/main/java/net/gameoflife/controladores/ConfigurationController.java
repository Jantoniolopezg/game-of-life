package net.gameoflife.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import lombok.RequiredArgsConstructor;
import net.gameoflife.objetos.configuracion.IndividuoConfiguracion;
import net.gameoflife.objetos.configuracion.JuegoConfiguracion;
import net.gameoflife.objetos.configuracion.RecursoConfiguracion;
import net.gameoflife.servicios.ConfiguracionServicio;
import net.gameoflife.utils.AlertUtil;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.nio.file.Path;

@Controller
@RequiredArgsConstructor
public class ConfigurationController extends BaseController{

    private static final Path CONFIGURATION_FILE_PATH = Path.of("./game-of-life.cfg");

    @FXML
    private Slider cloningProbabilitySlider;

    @FXML
    private Slider dwellProbabilitySlider;

    @FXML
    private Slider foodProbabilitySlider;

    @FXML
    private Spinner<Integer> gameHeightSpinner;

    @FXML
    private Spinner<Integer> gameWidthSpinner;

    @FXML
    private Slider globalReproductionImprovementFactorSlider;

    @FXML
    private Slider libraryCloningImprovementFactorSlider;

    @FXML
    private Slider libraryProbabilitySlider;

    @FXML
    private Slider lifeSlider;

    @FXML
    private Slider resourcesLifeSlider;

    @FXML
    private Spinner<Integer> maxIndividualsPerCellSpinner;

    @FXML
    private Spinner<Integer> maxResourcesPerCellSpinner;

    @FXML
    private Slider mountainProbabilitySlider;

    @FXML
    private Slider reproductionProbabilitySlider;

    @FXML
    private Slider treasureProbabilitySlider;

    @FXML
    private Slider treasureReproductionImprovementFactorSlider;

    @FXML
    private Slider waterProbabilitySlider;

    private final ConfiguracionServicio configuracionServicio;

    @Override
    public void init() {
        configuracionServicio.load(CONFIGURATION_FILE_PATH);
        initGameConfigurationControls();
        initIndividualConfigurationControls();
        initResourceConfigurationControls();
    }

    @FXML
    void onLoadConfigurationButton(javafx.event.ActionEvent event) {
        init();
    }

    @FXML
    void onSaveConfigurationButton(ActionEvent event) {
        configuracionServicio.save(CONFIGURATION_FILE_PATH);
        AlertUtil.showInfo("La configuración se ha guardado correctamente.");
    }

    private void initGameConfigurationControls(){
        final JuegoConfiguracion juegoConfiguracion = configuracionServicio.getConfiguracion().getJuegoConfiguracion();

        gameWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 11));
        gameWidthSpinner.getValueFactory().setValue(juegoConfiguracion.getSize().getX());
        gameWidthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            juegoConfiguracion.getSize().setX(newValue);
            configuracionServicio.getConfiguracion().setJuegoConfiguracion(juegoConfiguracion);
        });

        gameHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 9));
        gameHeightSpinner.getValueFactory().setValue(juegoConfiguracion.getSize().getY());
        gameHeightSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            juegoConfiguracion.getSize().setY(newValue);
            configuracionServicio.getConfiguracion().setJuegoConfiguracion(juegoConfiguracion);
        });

        maxIndividualsPerCellSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
        maxIndividualsPerCellSpinner.getValueFactory().setValue(juegoConfiguracion.getMaxIndividuosPorCasilla());
        maxIndividualsPerCellSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            juegoConfiguracion.setMaxIndividuosPorCasilla(newValue);
            configuracionServicio.getConfiguracion().setJuegoConfiguracion(juegoConfiguracion);
        });

        maxResourcesPerCellSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
        maxResourcesPerCellSpinner.getValueFactory().setValue(juegoConfiguracion.getMaxRecursosPorCasilla());
        maxResourcesPerCellSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            juegoConfiguracion.setMaxRecursosPorCasilla(newValue);
            configuracionServicio.getConfiguracion().setJuegoConfiguracion(juegoConfiguracion);
        });

        globalReproductionImprovementFactorSlider.setValue(juegoConfiguracion.getFactorMejoraPoblacion().doubleValue());
        globalReproductionImprovementFactorSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            juegoConfiguracion.setFactorMejoraPoblacion(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setJuegoConfiguracion(juegoConfiguracion);
        });

    }

    private void initIndividualConfigurationControls() {
        final IndividuoConfiguracion individuoConfiguracion = configuracionServicio.getConfiguracion().getIndividuoConfiguracion();

        lifeSlider.setValue(individuoConfiguracion.getVidaIndividuo());
        lifeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            individuoConfiguracion.setVidaIndividuo(newValue.intValue());
            configuracionServicio.getConfiguracion().setIndividuoConfiguracion(individuoConfiguracion);
        });

        reproductionProbabilitySlider.setValue(individuoConfiguracion.getReproduccionProbabilidad().doubleValue());
        reproductionProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            individuoConfiguracion.setReproduccionProbabilidad(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setIndividuoConfiguracion(individuoConfiguracion);
        });

        cloningProbabilitySlider.setValue(individuoConfiguracion.getClonacionProbabilidad().doubleValue());
        cloningProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            individuoConfiguracion.setClonacionProbabilidad(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setIndividuoConfiguracion(individuoConfiguracion);
        });

    }

    private void initResourceConfigurationControls(){
        final RecursoConfiguracion recursoConfiguracion = configuracionServicio.getConfiguracion().getRecursoConfiguracion();

        resourcesLifeSlider.setValue(recursoConfiguracion.getVidaRecursos());
        resourcesLifeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setVidaRecursos(newValue.intValue());
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        waterProbabilitySlider.setValue(recursoConfiguracion.getAguaProbabilidadRecurso().doubleValue());
        waterProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setAguaProbabilidadRecurso(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        foodProbabilitySlider.setValue(recursoConfiguracion.getComidaProbabilidadRecurso().doubleValue());
        foodProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setComidaProbabilidadRecurso(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        mountainProbabilitySlider.setValue(recursoConfiguracion.getMonteProbabilidadRecurso().doubleValue());
        mountainProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setMonteProbabilidadRecurso(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        treasureProbabilitySlider.setValue(recursoConfiguracion.getTesoroProbabilidadRecurso().doubleValue());
        treasureProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setTesoroProbabilidadRecurso(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        libraryProbabilitySlider.setValue(recursoConfiguracion.getBibliotecaProbabilidadRecurso().doubleValue());
        libraryProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setBibliotecaProbabilidadRecurso(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        dwellProbabilitySlider.setValue(recursoConfiguracion.getPozoProbabilidadRecurso().doubleValue());
        dwellProbabilitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setPozoProbabilidadRecurso(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        treasureReproductionImprovementFactorSlider.setValue(recursoConfiguracion.getTesoroFactorMejoraReproduccion().doubleValue());
        treasureReproductionImprovementFactorSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setTesoroFactorMejoraReproduccion(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });

        libraryCloningImprovementFactorSlider.setValue(recursoConfiguracion.getBibliotecaFactorMejoraClonacion().doubleValue());
        libraryCloningImprovementFactorSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            recursoConfiguracion.setBibliotecaFactorMejoraClonacion(BigDecimal.valueOf(newValue.doubleValue()));
            configuracionServicio.getConfiguracion().setRecursoConfiguracion(recursoConfiguracion);
        });
    }

}
