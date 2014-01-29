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

/**
 *
 * @author Nicklas
 */
public class PluginPolicy extends Policy {
    @Override
    public PermissionCollection getPermissions(CodeSource codeSource) {
        Permissions p = new Permissions();
        p.add(new PropertyPermission("WebLookAndFeel.*", "read"));
        p.add(new RuntimePermission("getClassLoader"));
        p.add(new LoggingPermission("control", null));
        return p;
    }

    @Override
    public void refresh() {
    }
}
