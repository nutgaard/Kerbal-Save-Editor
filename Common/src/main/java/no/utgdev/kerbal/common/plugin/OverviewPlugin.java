/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.plugin;

import javax.swing.tree.TreeModel;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 *
 * @author Nicklas
 */
public interface OverviewPlugin extends NamedPlugin {
    public String getName();
    public TreeModel getTreeModel(PropertyMap model);
    public void update(PropertyMap model);
}
