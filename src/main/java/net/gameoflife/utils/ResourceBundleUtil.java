package net.gameoflife.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;

import static org.apache.commons.lang3.StringUtils.EMPTY;

//Sirve para JavaFX
@Slf4j
public class ResourceBundleUtil {

    private final static String BUNDLE_NAME = "game-of-life";

    public static String getLabel(String key){
        String label = EMPTY;
        try {
            label = ResourceBundle.getBundle(BUNDLE_NAME).getString(key);
        }catch (Exception exception){
            log.error("Ha habido un error al leer la propiedad: " + key,exception);
        }
        return label;
    }

}
