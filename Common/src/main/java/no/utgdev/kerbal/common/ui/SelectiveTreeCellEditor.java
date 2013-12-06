/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.ui;

import com.alee.extended.image.WebImage;
import com.alee.extended.layout.HorizontalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.alee.laf.tree.UniqueNode;
import com.alee.laf.tree.WebTree;
import com.alee.laf.tree.WebTreeCellEditor;
import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import no.utgdev.kerbal.common.treemodel.Property;

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
    public Component getTreeCellEditorComponent(final JTree tree, final Object value, final boolean isSelected, final boolean expanded,
            final boolean leaf, final int row) {
        UniqueNode node = (UniqueNode) value;
        Property property = (Property) node.getUserObject();

        JPanel panel = new JPanel(new HorizontalFlowLayout(2, true));

//        panel.setMargin(0);
        panel.setBackground(Color.white);

        final JLabel component = (JLabel) tree.getCellRenderer().getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);
        if (component.getIcon() != null) {
            panel.add(new WebImage(component.getIcon()));
        }
        System.out.println("Property: " + property);
        System.out.println("Property: " + property.getClass());
        System.out.println("Property: " + property.getKey());
        System.out.println("Property: " + property.getValue());
        panel.add(new WebLabel(property.getKey() + ": "));
        WebTextField field = new WebTextField(property.getValue());
        field.setMinimumWidth(150);
        panel.add(field);

        return panel;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (super.isCellEditable(e) && tree.isEditable()) {
            TreePath path = tree.getSelectionPath();
            if (path == null) {
                return false;
            }
            TreeNode tn = (TreeNode) path.getLastPathComponent();
            if (tn == null) {
                return false;
            }
            return tn.isLeaf();
        }
        return false;
    }
}
