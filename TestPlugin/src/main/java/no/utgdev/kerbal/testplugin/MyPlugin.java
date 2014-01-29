package no.utgdev.kerbal.testplugin;

import com.alee.laf.menu.WebMenuItem;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.Init;
import no.utgdev.kerbal.common.plugin.OverviewContextMenuPlugin;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.common.treemodel.IProperty;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 * Hello world!
 *
 */
@PluginImplementation
public class MyPlugin implements ViewPlugin, OverviewContextMenuPlugin {
    
    @Init
    public void init() {
        
    }

    public String getName() {
        return this.getClass().getName();
    }

    public Component getView(PropertyMap model) {
        JPanel p = new JPanel();
        p.add(new JLabel(model.getName()));
        return p;
    }

    public void update(PropertyMap model) {
        
    }

    public boolean accept(IProperty property) {
        return true;
    }

    public WebMenuItem getComponent() {
        return new WebMenuItem("This is from testPlugin");
    }
}
