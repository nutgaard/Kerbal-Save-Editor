/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.i18n;

import java.util.Locale;
import java.util.ResourceBundle;
import no.utgdev.kerbal.common.Settings;
import no.utgdev.kerbal.common.plugin.NamedPlugin;
import no.utgdev.kerbal.common.plugin.PluginCache;

/**
 *
 * @author Nicklas
 */
public class I18n {

    private static I18n instance;
    private final ResourceBundle strings;

    public static I18n getInstance() {
        if (instance == null) {
            instance = new I18n();
        }
        return instance;
    }

    private I18n() {
        Settings settings = Settings.getInstance();

        Locale locale = new Locale(settings.getProperty("locale_lang"), settings.getProperty("locale_country"), settings.getProperty("locale_variant"));
        strings = ResourceBundle.getBundle("ResourceBundles.MessagesBundle", locale);
        System.out.println("Core: ");
        for (String key : strings.keySet()){
            System.out.println("    "+key+": "+strings.getString(key));
        }
        
        PluginCache<NamedPlugin> allPlugins = PluginCache.getInstance(NamedPlugin.class);
        for (NamedPlugin np : allPlugins.getList()) {
            if (np.hasI18n()) {
                ResourceBundle rb = ResourceBundle.getBundle("ResourceBundles."+np.getName(), locale, np.getClass().getClassLoader());
                System.out.println("I18n for "+np);
                for (String key : rb.keySet()) {
                    System.out.println("    "+key+": "+rb.getString(key));
                }
            }
        }
    }

    public String getString(String key) {
        return this.strings.getString(key);
    }
}
