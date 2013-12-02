/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.plugin;

import java.awt.Component;
import net.xeoh.plugins.base.Plugin;

/**
 *
 * @author Nicklas
 */
public interface ViewPlugin extends Plugin {
    public String getName();
    public Component getView();
}
