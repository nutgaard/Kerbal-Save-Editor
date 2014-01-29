/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.security;

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.util.PropertyPermission;
import java.util.logging.LoggingPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class PluginPolicy extends Policy {
    public static Logger logger = LoggerFactory.getLogger(PluginPolicy.class);

    @Override
    public PermissionCollection getPermissions(CodeSource codeSource) {
        logger.debug("Creating permissions for {}", codeSource.getLocation());
        Permissions p = new Permissions();
        logger.trace("Adding PropertyPermissions.");
        p.add(new PropertyPermission("WebLookAndFeel.*", "read"));
        logger.trace("Adding RuntimePermission for the method getClassLoader.");
        p.add(new RuntimePermission("getClassLoader"));
        logger.trace("Adding LoggingPermissions.");
        p.add(new LoggingPermission("control", null));
        return p;
    }

    @Override
    public void refresh() {
    }
}
