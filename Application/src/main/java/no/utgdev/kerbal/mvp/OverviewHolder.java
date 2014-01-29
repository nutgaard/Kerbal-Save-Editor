/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp;

import no.utgdev.kerbal.mvp.contextmenu.ContextMenuHandler;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tree.TreeSelectionStyle;
import com.alee.laf.tree.WebTree;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeModel;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.plugin.exception.PluginNotFoundException;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.common.ui.SelectiveTreeCellEditor;
import no.utgdev.kerbal.plugin.PluginCache;

/**
 *
 * @author Nicklas
 */
public class OverviewHolder extends JPanel {

    private static int borderSize = 5;
    private static PluginCache<OverviewPlugin> cache = PluginCache.getInstance(OverviewPlugin.class);
    private OverviewPlugin plugin;
    private PropertyMap model;

    public OverviewHolder(String pluginName, PropertyMap propertyMap) {
        super();
        this.plugin = findPlugin(pluginName);
        this.model = propertyMap;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, 0));
        initiate();
    }

    private OverviewPlugin findPlugin(String pluginName) throws PluginNotFoundException {
        OverviewPlugin p;

        try {
            p = cache.getPluginForName(pluginName);
        } catch (PluginNotFoundException ex) {
            p = cache.getList().get(0);
        }
        if (p == null) {
            throw new PluginNotFoundException(pluginName);
        }
        return p;
    }

    private void initiate() {
        TreeModel treemodel = this.plugin.getTreeModel(model);
        WebTree tree = new WebTree(treemodel);
        tree.setShowsRootHandles(true);
        tree.setEditable(true);
        TreeCellEditor editor = new SelectiveTreeCellEditor(tree);
        tree.setCellEditor(editor);

        tree.setSelectionMode(WebTree.CONTIGUOUS_TREE_SELECTION);
        tree.setSelectionStyle(TreeSelectionStyle.group);

        tree.addMouseListener(new ContextMenuHandler(tree));

        WebScrollPane scroll = new WebScrollPane(tree);
        scroll.setPreferredWidth(230);
        this.add(scroll);
    }
}
