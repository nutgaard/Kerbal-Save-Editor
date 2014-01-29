/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp.contextmenu;

import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import java.awt.Component;
import no.utgdev.kerbal.common.plugin.OverviewContextMenuPlugin;
import no.utgdev.kerbal.common.treemodel.IProperty;
import no.utgdev.kerbal.plugin.PluginCache;

/**
 *
 * @author Nicklas
 */
public class ContextMenu extends WebPopupMenu {
    
    public ContextMenu() {
        WebMenuItem wmi;
        
        add(new WebMenuItem("Item 1"));
        add(new WebMenuItem("Item 2"));
        addSeparator();
        add(new WebMenuItem("Item 3"));
    }
    public void show(Component invoker, int x, int y, IProperty property, boolean isLeaf, boolean isRoot) {
        removeAll();
        System.out.println("Populating context menu from plugins");
        PluginCache<OverviewContextMenuPlugin> plugins = PluginCache.getInstance(OverviewContextMenuPlugin.class);
        add(new WebMenuItem("FromPlugin"));
        for (OverviewContextMenuPlugin plugin : plugins.getList()) {
            if (plugin.accept(property)){
                add(plugin.getComponent());
            }
        }
        add(new WebMenuItem("Open"), 0);
        add(new WebMenuItem("Save"), 1);
        add(new WebMenuItem("Exit"), 2);
        addSeparator(3);
        add(new WebMenuItem("Test"));
        
        super.show(invoker, x, y);
    }
}
