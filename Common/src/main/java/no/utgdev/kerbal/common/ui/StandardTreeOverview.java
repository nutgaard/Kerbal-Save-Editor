package no.utgdev.kerbal.common.ui;


import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tree.TreeSelectionStyle;
import com.alee.laf.tree.WebTree;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.tree.TreeCellEditor;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 * Hello world!
 *
 */
@PluginImplementation
public class StandardTreeOverview implements OverviewPlugin{

    public String getName() {
        return "Treeview";
    }
    
    @Override
    public Component getView(PropertyMap model) {
        WebTree tree = new WebTree(TreeModelCreator.create(model));
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

    public void update(PropertyMap model) {
        
    }
    
}
