package no.utgdev.kerbal.testplugin;

import java.awt.Component;
import javax.swing.JPanel;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import no.utgdev.kerbal.common.plugin.ViewPlugin;

/**
 * Hello world!
 *
 */
@PluginImplementation
public class MyPlugin implements ViewPlugin {
    
    @Init
    public void init() {
        
    }

    public String getName() {
        System.out.println(getClass().getName() + ": user.home: " + System.getProperty("user.home"));
        return "Hei";
    }

    public Component getView() {
        return new JPanel();
    }
}
