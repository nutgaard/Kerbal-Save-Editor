package no.utgdev.kerbal.testplugin;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

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
//        System.out.println(getClass().getName() + ": user.home: " + System.getProperty("user.home"));
        return this.getClass().getName();
    }

    public Component getView(PropertyMap model) {
        JPanel p = new JPanel();
        p.add(new JLabel(model.getName()));
        return p;
    }

    public void update(PropertyMap model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
