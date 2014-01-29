/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class Settings extends Properties {

    public static Logger logger = LoggerFactory.getLogger(Settings.class);
    private static Settings instance;

    private Settings() {
        put("locale_lang", "en");
        put("locale_country", "GB");
        put("locale_variant", "");
        loadDefaults();
    }

    private void loadDefaults() {
        logger.info("Loading settings.");
        try {
            super.load(new FileReader(new File("./settings.properties")));
        } catch (Exception ex) {
            logger.warn("Could not find settings file, continueing using default settings", ex);
        }
        logger.info("Settings loading complete.");
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }
}
