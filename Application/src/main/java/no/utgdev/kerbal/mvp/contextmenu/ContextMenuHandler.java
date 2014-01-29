/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp.contextmenu;

import com.alee.laf.tree.UniqueNode;
import com.alee.laf.tree.WebTree;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;
import no.utgdev.kerbal.common.treemodel.IProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class ContextMenuHandler extends MouseAdapter {

    public static Logger logger = LoggerFactory.getLogger(ContextMenuHandler.class);
    private ContextMenu menu;
    private WebTree tree;

    public ContextMenuHandler(WebTree tree) {
        this.tree = tree;
        this.menu = new ContextMenu();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            logger.debug("Received mouseClick event.");
            TreePath path = tree.getPathForLocation(e.getPoint());
            Rectangle bounds = tree.getUI().getPathBounds(tree, path);
            logger.trace("Verify that rightclick was infact inside of a node element.");
            if (bounds != null && bounds.contains(e.getPoint())) {
                UniqueNode node = (UniqueNode) tree.getClosestNodeForLocation(e.getPoint());
                logger.trace("Having a contextmenu on the root element does make sense atm.");
                if (!node.isRoot()) {
                    IProperty property = (IProperty) node.getUserObject();

                    logger.trace("Force menu placement.");
                    logger.trace("Bounds: {} {} {} {}", bounds.x, bounds.y, bounds.width, bounds.height);
                    int x = bounds.x + 10;
                    int y = bounds.y + bounds.height / 2;
                    logger.debug("Showing contextmenu.");
                    menu.show(e.getComponent(), x, y, property, node.isLeaf(), node.isRoot());
                }
            }
        }
    }
}
