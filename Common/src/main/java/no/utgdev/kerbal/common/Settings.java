/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 *
 * @author Nicklas
 */
public class Settings extends Properties {
    private static Settings instance;
    private Settings() {
        put("locale_lang", "en");
        put("locale_country", "GB");
        put("locale_variant", "");
        loadDefaults();
    }
    private void loadDefaults() {
        try {
            super.load(new FileReader(new File("./settings.properties")));
        } catch (Exception ex) {
            System.out.println("Could not find settings file, continueing using defaults settigns");
        }
    }
    public static Settings getInstance() {
        if (instance == null){
            instance = new Settings();
        }
        return instance;
    }
}
