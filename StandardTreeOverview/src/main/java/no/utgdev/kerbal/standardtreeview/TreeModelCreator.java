/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.standardtreeview;

import com.alee.laf.tree.UniqueNode;
import com.google.common.collect.Lists;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.treemodel.IProperty;
import no.utgdev.kerbal.common.treemodel.Property;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

@PluginImplementation
public class TreeModelCreator implements OverviewPlugin {

    public String getName() {
        return "Treeview";
    }

    public TreeModel getTreeModel(PropertyMap model) {
        UniqueNode root = new UniqueNode(model.getName());
        
        recursiveAddToParent(root, model);
        
        return new DefaultTreeModel(root);
    }

    public void update(PropertyMap model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static void recursiveAddToParent(DefaultMutableTreeNode parent, IProperty parentMap) {
        List<Property> properties = Lists.newLinkedList();
        
        for (IProperty property : parentMap.getChildren()) {
            if (property instanceof PropertyMap) {
                PropertyMap map = (PropertyMap)property;
                UniqueNode newParent = new UniqueNode(map);
                parent.add(newParent);
                recursiveAddToParent(newParent, property);
            }else {
                properties.add((Property)property);
            }
        }
        for (Property property : properties){
            UniqueNode propertyParent = new UniqueNode(property);
            parent.add(propertyParent);
        }
    }
    @Override
    public boolean hasI18n() {
        return false;
    }
}
