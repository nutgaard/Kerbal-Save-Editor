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
public class Property implements IProperty {

    private String key, value;

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public List<IProperty> getChildren() {
        return new LinkedList<>();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value){
        this.value = value;
    }
    
    @Override
    public String print(int intendation) {
        StringBuilder sb = new StringBuilder();
        sb.append(OutputHelper.createIndentation(intendation)).append(key).append(" = ").append(value).append("\n");
        return sb.toString();
    }
    @Override
    public String toString() {
        return key+": "+value;
    }
}
