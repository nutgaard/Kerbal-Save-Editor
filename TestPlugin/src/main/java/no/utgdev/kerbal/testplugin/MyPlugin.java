package no.utgdev.kerbal.testplugin;

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

    public String getTabName() {
        System.out.println(getClass().getName() + ": user.home: " + System.getProperty("user.home"));
        return "Hei";
    }

    public JPanel getView() {
        return new JPanel();
    }
}
