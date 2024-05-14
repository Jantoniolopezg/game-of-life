package net.gameoflife.utils;

import javafx.scene.control.Alert;

//Hace que salga una pantallita de aviso
public class AlertUtil {

    public static void showInfo(String content){
        showDialog(Alert.AlertType.INFORMATION, "Informacion",content);
    }

    private static void showDialog(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
