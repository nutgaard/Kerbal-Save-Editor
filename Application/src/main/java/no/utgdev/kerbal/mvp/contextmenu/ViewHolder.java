/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp.contextmenu;

import com.alee.laf.tabbedpane.WebTabbedPane;
import javax.swing.BorderFactory;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.plugin.PluginCache;

/**
 *
 * @author Nicklas
 */
public class ViewHolder extends WebTabbedPane {
    private static int borderSize = 5;
    private static PluginCache<ViewPlugin> cache = PluginCache.getInstance(ViewPlugin.class);
    private PropertyMap savefileModel;

    public ViewHolder(PropertyMap model) {
        super(TOP);
        setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
        this.savefileModel = model;
        initiate();
    }
    private void initiate() {
        for (ViewPlugin vp : cache.getList()) {
            add(vp.getName(), vp.getView(savefileModel));
        }
    }
    
}
