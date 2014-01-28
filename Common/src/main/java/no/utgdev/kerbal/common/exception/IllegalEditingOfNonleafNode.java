/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.exception;

/**
 *
 * @author Nicklas
 */
public class IllegalEditingOfNonleafNode extends RuntimeException {

    public IllegalEditingOfNonleafNode() {
        super("Edition of nonleaf nodes are not permitted");
    }    
}
