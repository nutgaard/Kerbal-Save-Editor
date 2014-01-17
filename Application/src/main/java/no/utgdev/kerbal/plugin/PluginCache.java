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
import no.utgdev.kerbal.common.plugin.NamedPlugin;
import no.utgdev.kerbal.common.plugin.exception.PluginNotFoundException;

/**
 *
 * @author Nicklas
 */
public class PluginCache<T extends NamedPlugin> {

    private final ImmutableList<T> plugins;

    private PluginCache(Collection<T> plugins) {
        this.plugins = ImmutableList.copyOf(plugins);
    }
    public ImmutableList<T> getList() {
        return this.plugins;
    }
    public T getPluginForName(String name) throws PluginNotFoundException {
        for (T t : plugins) {
            if (name.equals(t.getName())){
                return t;
            }
        }
        throw new PluginNotFoundException(name);
    }
    private static final Map<Class<? extends Plugin>, PluginCache> instances = new HashMap<>();

    public static <T extends Plugin> void create(Class<T> cls, Collection<T> plugins) {
        synchronized (instances) {
            PluginCache instance = new PluginCache(plugins);
            instances.put(cls, instance);
        }
    }

    public static <T extends NamedPlugin> PluginCache<T> getInstance(Class<T> cls) {
        synchronized (instances) {
            PluginCache instance = instances.get(cls);
            if (instance == null) {
                throw new IllegalArgumentException("Class: " + cls + " could not be found in plugin cache");
            }
            return instance;
        }
    }
}
