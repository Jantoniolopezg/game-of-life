package net.gameoflife.utils;

import java.util.ResourceBundle;

//Sirve para JavaFX.
public class ResourceBundleUtil {

    private final static String BUNDLE_NAME = "game-of-life";

    public static String getLabel(String key){
        return ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
    }

}
