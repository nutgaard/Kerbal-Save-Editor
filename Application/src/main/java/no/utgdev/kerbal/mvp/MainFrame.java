/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.tree.TreeSelectionStyle;
import com.alee.laf.tree.WebTree;
import com.alee.laf.tree.WebTreeCellRenderer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.tree.TreeCellEditor;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.i18n.Resources;
import no.utgdev.kerbal.mvp.utils.tree.SelectiveTreeCellEditor;
import no.utgdev.kerbal.mvp.utils.tree.TreeModelCreator;

/**
 *
 * @author Nicklas
 */
public class MainFrame extends JFrame {
    private static ResourceBundle i18n = Resources.strings;
    private PropertyMap rootMap;

    public MainFrame(PropertyMap rootMap) throws HeadlessException {
        super(i18n.getString("greetings"));
        this.rootMap = rootMap;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        getContentPane().add(new JLabel(i18n.getString("farewell")), BorderLayout.CENTER);
        
        
        WebLabel label2 = new WebLabel("Right", WebLabel.CENTER);
        label2.setMargin(5);
        WebPanel panel2 = new WebPanel(true, label2);
        
        WebSplitPane split = new WebSplitPane(JSplitPane.HORIZONTAL_SPLIT, createTreeView(rootMap), panel2);
        split.setOneTouchExpandable(true);
        split.setPreferredSize(new Dimension(250, 200));
        split.setDividerLocation(125);
        split.setContinuousLayout(true);
        
        
        getContentPane().add(split);
        pack();
        setVisible(true);
    }
    private static Component createTreeView(PropertyMap rootMap) {
        WebTree tree = new WebTree(TreeModelCreator.create(rootMap));
        tree.setShowsRootHandles(true);
        tree.setEditable(true);
        TreeCellEditor editor = new SelectiveTreeCellEditor();
        tree.setCellEditor(editor);
        
        tree.setSelectionMode(WebTree.CONTIGUOUS_TREE_SELECTION);
        tree.setSelectionStyle(TreeSelectionStyle.group);
        
        WebScrollPane scroll = new WebScrollPane(tree);
        scroll.setPreferredSize(new Dimension(125, 150) );
        return scroll;
    }
    private static String lorem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
}
