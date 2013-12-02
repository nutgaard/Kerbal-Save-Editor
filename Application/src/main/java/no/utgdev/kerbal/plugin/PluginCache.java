/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.plugin;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.xeoh.plugins.base.Plugin;

/**
 *
 * @author Nicklas
 */
public class PluginCache<T extends Plugin> {

    private final ImmutableList<T> plugins;

    private PluginCache(Collection<T> plugins) {
        this.plugins = ImmutableList.copyOf(plugins);
    }
    public ImmutableList<T> getList() {
        return this.plugins;
    }
    private static final Map<Class<? extends Plugin>, PluginCache> instances = new HashMap<>();

    public static <T extends Plugin> void create(Class<T> cls, Collection<T> plugins) {
        synchronized (instances) {
            PluginCache instance = new PluginCache(plugins);
            instances.put(cls, instance);
        }
    }

    public static PluginCache getInstance(Class<? extends Plugin> cls) {
        synchronized (instances) {
            PluginCache instance = instances.get(cls);
            if (instance == null) {
                throw new IllegalArgumentException("Class: " + cls + " could not be found in plugin cache");
            }
            return instance;
        }
    }
}
