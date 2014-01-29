/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.plugin;

import com.alee.laf.menu.WebMenuItem;
import no.utgdev.kerbal.common.treemodel.IProperty;

/**
 *
 * @author Nicklas
 */
public interface OverviewContextMenuPlugin extends NamedPlugin {

    public boolean accept(IProperty property);

    public WebMenuItem getComponent();
}
