/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.treemodel;

import java.util.List;

/**
 *
 * @author Nicklas
 */
public interface IProperty {
    public List<IProperty> getChildren();
    
    public String print(int indentation);
}
