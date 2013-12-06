/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.compacttreeview;

import com.alee.laf.tree.UniqueNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import no.utgdev.kerbal.common.treemodel.IProperty;
import no.utgdev.kerbal.common.treemodel.Property;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 *
 * @author Nicklas
 */
public class CompactTreeModelCreator {
    public static TreeModel create(PropertyMap rootMap){
        UniqueNode root = new UniqueNode(rootMap.getName());
        
        recursiveAddToParent(root, rootMap);
        
        return new DefaultTreeModel(root);
    }
//    private static void recursiveAddToParent(DefaultMutableTreeNode parent, IProperty parentMap) {
//        List<Property> properties = Lists.newLinkedList();
//        Map<String, List<PropertyMap>> equals = Maps.newLinkedHashMap();
//        
//        for (IProperty property : parentMap.getChildren()) {
//            if (property instanceof PropertyMap) {
//                PropertyMap map = (PropertyMap)property;
//                put(equals, map);
//            }else {
//                properties.add((Property)property);
//            }
//        }
//        for (Entry<String, List<PropertyMap>> entry : equals.entrySet()){
//            if (entry.getValue().size() > 1) {
//                //createParent
//                UniqueNode newParent = new UniqueNode(entry.getKey()+"'S");
//                parent.add(newParent);
//                for (PropertyMap pm : entry.getValue()){
//                    UniqueNode newnewParent = new UniqueNode(pm);
//                    newParent.add(newnewParent);
//                    recursiveAddToParent(newnewParent, pm);
//                }
//            }else {
//                UniqueNode newParent = new UniqueNode(entry.getValue().get(0));
//                parent.add(newParent);
//                recursiveAddToParent(newParent, entry.getValue().get(0));
//            }
//        }
//        for (Property property : properties){
//            UniqueNode propertyParent = new UniqueNode(property);
//            parent.add(propertyParent);
//        }
//    }
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

    private static void put(Map<String, List<PropertyMap>> equals, PropertyMap map) {
        if (!equals.containsKey(map.getName())){
            equals.put(map.getName(), new LinkedList<PropertyMap>());
        }
        equals.get(map.getName()).add(map);
    }
}
