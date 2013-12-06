package no.utgdev.kerbal.compacttreeview;

import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tree.TreeSelectionStyle;
import com.alee.laf.tree.WebTree;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.tree.TreeCellEditor;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.common.ui.SelectiveTreeCellEditor;

/**
 * Hello world!
 *
 */

@PluginImplementation
public class CompactTreeView implements OverviewPlugin {

    @Override
    public String getName() {
        return "Compact Tree";
    }

    @Override
    public Component getView(PropertyMap model) {
        WebTree tree = new WebTree(CompactTreeModelCreator.create(model));
        tree.setShowsRootHandles(true);
        tree.setEditable(true);
        TreeCellEditor editor = new SelectiveTreeCellEditor(tree);
        tree.setCellEditor(editor);
        
        tree.setSelectionMode(WebTree.CONTIGUOUS_TREE_SELECTION);
        tree.setSelectionStyle(TreeSelectionStyle.group);
        
        WebScrollPane scroll = new WebScrollPane(tree);
        scroll.setPreferredSize(new Dimension(300, 150) );
        return scroll;
    }

    @Override
    public void update(PropertyMap model) {
        
    }

}
