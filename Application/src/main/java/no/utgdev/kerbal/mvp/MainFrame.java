/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp;

import com.alee.laf.splitpane.WebSplitPane;
import java.awt.Component;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import no.utgdev.kerbal.common.Settings;
import no.utgdev.kerbal.common.i18n.I18N;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.mvp.contextmenu.ViewHolder;

/**
 *
 * @author Nicklas
 */
public class MainFrame extends JFrame {

    private static I18N i18n = I18N.getInstance();
    private static Settings settings = Settings.getInstance();
    
    private PropertyMap rootMap;

    public MainFrame(PropertyMap rootMap) throws HeadlessException {
        super(i18n.getString("window.title"));
        this.rootMap = rootMap;
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final WebSplitPane splitpane = createLayout();

        getContentPane().add(splitpane);

        splitpane.setLeftComponent(createOverview());
        splitpane.setRightComponent(createTabbedView());



        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private WebSplitPane createLayout() {
        WebSplitPane splitpane = new WebSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitpane.setOneTouchExpandable(true);
        splitpane.setDividerLocation(470);
        splitpane.setContinuousLayout(true);
        return splitpane;
    }

    private Component createTabbedView() {
        return new ViewHolder(rootMap);
    }

    private Component createOverview() {
        String overviewName = settings.getProperty("defaultOverview");
        return new OverviewHolder(overviewName, rootMap);
    }
}
