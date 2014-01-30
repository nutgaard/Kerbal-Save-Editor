/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common;

import java.io.File;
import java.io.FileOutputStream;
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
    private int defaultsSize;

    private Settings() {
        put("locale_lang", "en");
        put("locale_country", "GB");
        put("locale_variant", "");
        put("logging.file.level", "trace");
        put("logging.stdout.level", "debug");

        put("defaultOverview", "Compact Tree");
        put("file.open", "");

        defaultsSize = size();
        loadDefaults();
    }

    private void loadDefaults() {
        logger.info("Loading settings.");
        File file = new File("./settings.properties");
        Properties p = new Properties();
        int loadedSize = 0;
        try {
            if (file.exists()) {
                logger.debug("Settings file found, loading.");
                p.load(new FileReader(file));
                loadedSize = p.size();

                putAll(p);
                if (loadedSize < defaultsSize) {
                    logger.debug("Settingsfile was missing values, recreating settingsfile.");
                    super.store(new FileOutputStream(file), "Settings file for Kerbal Save Editor");
                }
            } else {
                logger.debug("Settingsfile did not exist, recreating settingsfile.");
                super.store(new FileOutputStream(file), "Settings file for Kerbal Save Editor");
            }
        } catch (Exception ex) {
            logger.warn("An exception was caught loading or storing the settings file.", ex);
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
