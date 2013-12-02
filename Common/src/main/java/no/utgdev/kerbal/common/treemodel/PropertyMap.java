/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.treemodel;

import java.util.LinkedList;
import java.util.List;
import no.utgdev.kerbal.common.utils.OutputHelper;

/**
 *
 * @author Nicklas
 */
public class PropertyMap implements IProperty {

    private final String name;
    private List<IProperty> children;

    public PropertyMap(String name) {
        this.name = name;
        this.children = new LinkedList<>();
    }
    public void addChild(IProperty p){
        this.children.add(p);
    }

    public String getName() {
        return name;
    }
    
    @Override
    public List<IProperty> getChildren() {
        return this.children;
    }
    
    @Override
    public String print(int indentationNum) {
        StringBuilder sb = new StringBuilder();
        String indentation = OutputHelper.createIndentation(indentationNum);
        sb.append(indentation).append(this.name).append("\n");
        sb.append(indentation).append("{").append("\n");
        for (IProperty property : this.children) {
            sb.append(property.print(indentationNum+1));
        }
        sb.append(indentation).append("}").append("\n");
        
        return sb.toString();
    }
    @Override
    public String toString() {
        return name;
    }
}
