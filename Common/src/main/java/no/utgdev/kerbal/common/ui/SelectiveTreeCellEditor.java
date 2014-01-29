/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.ui;

import com.alee.extended.layout.HorizontalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.laf.tree.UniqueNode;
import com.alee.laf.tree.WebTree;
import com.alee.laf.tree.WebTreeCellEditor;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import javax.swing.CellEditor;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import no.utgdev.kerbal.common.exception.IllegalEditingOfNonleafNode;
import no.utgdev.kerbal.common.i18n.I18n;
import no.utgdev.kerbal.common.plugin.exception.IllegalUserObjectException;
import no.utgdev.kerbal.common.treemodel.IProperty;
import no.utgdev.kerbal.common.treemodel.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class SelectiveTreeCellEditor extends WebTreeCellEditor {

    public static Logger logger = LoggerFactory.getLogger(I18n.class);
    private WebTree tree;
    private Editor editor;

    public SelectiveTreeCellEditor(WebTree tree) {
        this.tree = tree;

    }

    @Override
    public Component getTreeCellEditorComponent(final JTree tree, final Object value, final boolean isSelected, final boolean expanded, final boolean leaf, final int row) {
        if (!leaf) {
            throw new IllegalEditingOfNonleafNode();
        }

        UniqueNode node = (UniqueNode) value;
        IProperty property = (IProperty) node.getUserObject();

        if (!(property instanceof Property)) {
            throw new IllegalUserObjectException(property.getClass().toString());
        }
        final JLabel renderedComponent = (JLabel) tree.getCellRenderer().getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, leaf);

        this.editor = new Editor((Property) property, renderedComponent.getIcon(), this);

        return this.editor;
    }

    @Override
    public Object getCellEditorValue() {
        return this.editor.getCellEditorValue();
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

class Editor extends WebPanel implements CellEditor {

    private List<CellEditorListener> listeners;
    private Property property;
    private final WebLabel label = new WebLabel();
    private final WebTextField text = new WebTextField();
    private Icon icon;
    private final WebTreeCellEditor editor;

    public Editor(Property property, Icon icon, final WebTreeCellEditor editor) {
        this.property = property;
        this.icon = icon;
        this.editor = editor;
        listeners = new LinkedList<>();
        setLayout(new HorizontalFlowLayout(2, true));
        setBackground(Color.white);
        text.setMinimumWidth(150);

        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stopCellEditing()) {
                    fireEditingStopped();
                }
            }
        });
        add(label);
        add(text);
        update();
    }

    public final void update() {
        clear();
        if (icon != null) {
            label.setIcon(icon);
        }
        label.setText(property.getKey() + ":");
        text.setText(property.getValue());
    }

    private void clear() {
        label.setText("");
        text.setText("");
        label.setIcon(null);
    }

    @Override
    public Object getCellEditorValue() {
        return this.property;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        this.property.setValue(text.getText());
        this.editor.stopCellEditing();
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        this.editor.cancelCellEditing();
        fireEditingCanceled();
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        this.listeners.add(l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        this.listeners.remove(l);
    }

    private void fireEditingStopped() {
        ChangeEvent ce = new ChangeEvent(this);
        for (CellEditorListener l : listeners) {
            l.editingStopped(ce);
        }
    }

    private void fireEditingCanceled() {
        ChangeEvent ce = new ChangeEvent(this);
        for (CellEditorListener l : listeners) {
            l.editingCanceled(ce);
        }
    }
}
