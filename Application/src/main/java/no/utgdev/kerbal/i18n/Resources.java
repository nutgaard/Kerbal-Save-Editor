/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.i18n;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class Resources {
    public final static ResourceBundle strings;
    public final static Properties settings;
    static {
        settings = new Properties();
        settings.put("locale_lang", "en");
        settings.put("locale_country", "GB");
        settings.put("locale_variant", "");
        try {
            settings.load(new FileReader(new File("./settings.properties")));
        } catch (Exception ex) {
            System.out.println("Count not find settings file, continueing using default settings.");
        }
        Locale locale = new Locale(settings.getProperty("locale_lang"), settings.getProperty("locale_country"), settings.getProperty("locale_variant"));
        strings = ResourceBundle.getBundle("ResourceBundles.MessagesBundle", locale);
    }
}
