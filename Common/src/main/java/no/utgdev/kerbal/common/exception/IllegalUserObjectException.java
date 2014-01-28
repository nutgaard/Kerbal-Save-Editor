/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.plugin.exception;

/**
 *
 * @author Nicklas
 */
public class IllegalUserObjectException extends RuntimeException {

    public IllegalUserObjectException(String pluginName) {
        super("Could not find a plugin named \""+pluginName+"\"");
    }    
}
