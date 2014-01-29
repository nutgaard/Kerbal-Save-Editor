/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.i18n;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.InitializationException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import no.utgdev.kerbal.common.Settings;
import no.utgdev.kerbal.common.plugin.NamedPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class I18N {

    public static Logger logger = LoggerFactory.getLogger(I18N.class);
    private static I18N instance;
    private final Map<String, String> strings;
    private final Locale locale;

    public static void initiate(List<NamedPlugin> plugins) {
        logger.info("Initiating I18n");
        if (instance != null) {
            throw new InitializationException("Tried to reinitiate I18n.");
        }
        instance = new I18N(plugins);
        logger.info("Initiation of I18n complete.");
    }

    public static I18N getInstance() {
        if (instance == null) {
            throw new InitializationException("I18n has not been initiated properly.");
        }
        return instance;
    }

    private I18N(List<NamedPlugin> plugins) {
        Settings settings = Settings.getInstance();
        this.strings = Maps.newHashMap();
        this.locale = new Locale(settings.getProperty("locale_lang"), settings.getProperty("locale_country"), settings.getProperty("locale_variant"));

        loadI18N("Core", I18N.class.getClassLoader());
        loadPluginI18N(plugins);
    }

    public String getString(String key) {
        return this.strings.get(key);
    }

    private void loadI18N(String name, ClassLoader loader) {
        logger.info("I18N definitions for {}.", name);
        try {
            ResourceBundle rb = ResourceBundle.getBundle("ResourceBundles." + name, this.locale, loader);
            for (String key : rb.keySet()) {
                String value = rb.getString(key);
                this.strings.put(key, value);
                logger.debug("  {}:{}", key, value);
            }
        } catch (Exception e) {
            logger.error("Error loading I18n for " + name, e);
        }
    }

    private void loadPluginI18N(List<NamedPlugin> plugins) {
        for (NamedPlugin np : plugins) {
            if (np.hasI18n()) {
                loadI18N(np.getName(), np.getClass().getClassLoader());
            }
        }
    }
}
