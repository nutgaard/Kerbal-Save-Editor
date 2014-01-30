/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.treemodel.marshallingObjects;

import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 *
 * @author Nicklas
 */
public class Crew extends PropertyMap {
    Crew(String name){
        super(name);
    }
    public Crew() {
        super("CREW");
    }    
}
