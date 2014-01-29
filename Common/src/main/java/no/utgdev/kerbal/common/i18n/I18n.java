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


/**
 *
 * @author Nicklas
 */
public class I18n {

    private static I18n instance;
    private final ResourceBundle strings;

    public static void initiate(List<NamedPlugin> plugins) {
        if (instance != null) {
            throw new InitializationException("I18n has allready been initiliazed.");
        }
        instance = new I18n(plugins);
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
        System.out.println("Core: ");
        for (String key : strings.keySet()) {
            System.out.println("    " + key + ": " + strings.getString(key));
        }

        for (NamedPlugin np : plugins) {
            if (np.hasI18n()) {
                ResourceBundle rb = ResourceBundle.getBundle("ResourceBundles." + np.getName(), locale, np.getClass().getClassLoader());
                System.out.println("I18n for " + np);
                for (String key : rb.keySet()) {
                    System.out.println("    " + key + ": " + rb.getString(key));
                }
            }
        }
    }

    public String getString(String key) {
        return this.strings.getString(key);
    }
}
