package net.gameoflife.appconfig;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gameoflife.enumeraciones.FXMLDefinition;

import java.util.Objects;

@Slf4j
@Getter
@RequiredArgsConstructor
public class StageManager {

    private final Stage primarySatge;
    private final SpringFXMLLoader springFXMLLoader;
    private Stage modalStage;
    private FXMLDefinition fxmlDefinition;

    public void switchScene(final FXMLDefinition fxmlDefinition){
        final Parent rootNode = loadFxml(fxmlDefinition.getFxmlFile());
        this.fxmlDefinition = fxmlDefinition;
        if (fxmlDefinition.isModal()){
            showModalScene(rootNode,fxmlDefinition);
        }else {
            show(rootNode, fxmlDefinition);
        }
    }

    private void show(final Parent rootNode, FXMLDefinition fxmlDefinition){
        final Scene scene = getSceneWithRootNode(rootNode);
        primarySatge.setTitle(fxmlDefinition.getTitle());
        primarySatge.setScene(scene);

        final boolean isMaximiced = fxmlDefinition.isMaximized();
        if (isMaximiced){
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            primarySatge.setWidth(bounds.getWidth());
            primarySatge.setMinWidth(bounds.getWidth());
            primarySatge.setHeight(bounds.getHeight());
            primarySatge.setMinHeight(bounds.getHeight());
            primarySatge.setMaxWidth(bounds.getWidth());
            primarySatge.setMaxHeight(bounds.getHeight());
        }else {
            double prefWidth = ((Pane) rootNode).getPrefWidth();
            double prefHeight = ((Pane) rootNode).getPrefHeight() + 30;
            primarySatge.setWidth(prefWidth);
            primarySatge.setMaxWidth(prefWidth);
            primarySatge.setMinWidth(prefWidth);
            primarySatge.setHeight(prefHeight);
            primarySatge.setMaxHeight(prefHeight);
            primarySatge.setMinHeight(prefHeight);
        }
        primarySatge.setMaximized(isMaximiced);
        primarySatge.centerOnScreen();
        primarySatge.setResizable(fxmlDefinition.isResizable());
        primarySatge.getIcons().add(fxmlDefinition.getIcon());

        try {
            primarySatge.show();
        }catch (Exception exception){
            log.error("Incapaz de mostrar ecena con titulo {}. {}", fxmlDefinition.getTitle(), exception.getMessage());
            Platform.exit();
        }
    }

    private void showModalScene(Parent rootNode, FXMLDefinition view){
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(primarySatge);
        modalStage.setTitle(view.getTitle());
        modalStage.setResizable(view.isResizable());
        modalStage.getIcons().add(view.getIcon());
        final Scene scene = new Scene(rootNode);
        modalStage.setScene(scene);
        modalStage.show();
    }

    private Scene getSceneWithRootNode(Parent rootNode){
        Scene scene = primarySatge.getScene();
        if (scene == null){
            return new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }

    /**
     * Carga un documento FXML y devuelve el nodo raiz.
     */
    private Parent loadFxml(String fxmlFilePath){
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath).getParent();
            Objects.requireNonNull(rootNode,"Un nodo Raiz FXML debe de no ser null");
        }catch (Exception exception){
            log.error("Incapaz de cargar FXML {}. {}", fxmlFilePath, exception);
            Platform.exit();
        }
        return rootNode;
    }
}
