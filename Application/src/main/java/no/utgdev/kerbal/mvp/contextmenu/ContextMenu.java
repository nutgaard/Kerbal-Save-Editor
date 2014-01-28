/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp.contextmenu;

import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import java.awt.Component;
import no.utgdev.kerbal.common.treemodel.IProperty;

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
        System.out.println("Populating context menu from plugins");
        super.show(invoker, x, y);
    }
}
