/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp.utils.tree;

import com.alee.laf.tree.WebTree;
import com.alee.laf.tree.WebTreeCellEditor;
import java.util.EventObject;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Nicklas
 */
public class SelectiveTreeCellEditor extends WebTreeCellEditor {
    private WebTree tree;

    public SelectiveTreeCellEditor(WebTree tree) {
        this.tree = tree;
    }
    
    

    @Override
    public boolean isCellEditable(EventObject e) {
        if (super.isCellEditable(e) && tree.isEditable()){
            TreePath path = tree.getSelectionPath();
            return ((TreeNode)path.getLastPathComponent()).isLeaf();
        }
        return false;
    }
}
