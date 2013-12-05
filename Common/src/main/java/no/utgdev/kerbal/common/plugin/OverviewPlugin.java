/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.plugin;

import java.awt.Component;
import net.xeoh.plugins.base.Plugin;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 *
 * @author Nicklas
 */
public interface OverviewPlugin extends Plugin {
    public String getName();
    public Component getView(PropertyMap model);
    public void update(PropertyMap model);
}
