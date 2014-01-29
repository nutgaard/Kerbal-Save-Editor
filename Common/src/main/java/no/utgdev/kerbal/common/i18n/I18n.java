/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.i18n;

import com.thoughtworks.xstream.InitializationException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import no.utgdev.kerbal.common.Settings;
import no.utgdev.kerbal.common.plugin.NamedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class I18n {

    public static Logger logger = LoggerFactory.getLogger(I18n.class);
    private static I18n instance;
    private final ResourceBundle strings;

    public static void initiate(List<NamedPlugin> plugins) {
        logger.info("Initiating I18n");
        if (instance != null) {
            throw new InitializationException("Tried to reinitiate I18n.");
        }
        instance = new I18n(plugins);
        logger.info("Initiation of I18n complete.");
    }

    public static I18n getInstance() {
        if (instance == null) {
            throw new InitializationException("I18n has not been initiated properly.");
        }
        return instance;
    }

    private I18n(List<NamedPlugin> plugins) {
        Settings settings = Settings.getInstance();

        Locale locale = new Locale(settings.getProperty("locale_lang"), settings.getProperty("locale_country"), settings.getProperty("locale_variant"));
        strings = ResourceBundle.getBundle("ResourceBundles.MessagesBundle", locale);
        logger.info("Core I18n definitions: ");
        for (String key : strings.keySet()) {
            logger.debug("  {}:{}", key, strings.getString(key));
        }

        for (NamedPlugin np : plugins) {
            if (np.hasI18n()) {
                try {
                    ResourceBundle rb = ResourceBundle.getBundle("ResourceBundles." + np.getName(), locale, np.getClass().getClassLoader());
                    logger.info("I18n definitions for {}", np);
                    for (String key : rb.keySet()) {
                        logger.debug("  {}:{}", key, rb.getString(key));
                    }
                } catch (Exception e) {
                    logger.error("Error loading I18n for "+np, e);
                }
            }
        }
    }

    public String getString(String key) {
        return this.strings.getString(key);
    }
}
