/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.mvp;

import com.alee.laf.splitpane.WebSplitPane;
import com.google.common.collect.ImmutableList;
import java.awt.Component;
import java.awt.HeadlessException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.i18n.Resources;
import no.utgdev.kerbal.plugin.PluginCache;

/**
 *
 * @author Nicklas
 */
public class MainFrame extends JFrame {

    private static ResourceBundle i18n = Resources.strings;
    private static Properties settings = Resources.settings;
    private static ImmutableList<ViewPlugin> viewPlugins = PluginCache.getInstance(ViewPlugin.class).getList();
    private static ImmutableList<OverviewPlugin> overviewPlugins = PluginCache.getInstance(OverviewPlugin.class).getList();
    private PropertyMap rootMap;

    public MainFrame(PropertyMap rootMap) throws HeadlessException {
        super(i18n.getString("window.title"));
        this.rootMap = rootMap;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        getContentPane().add(new JLabel(i18n.getString("farewell")), BorderLayout.CENTER);

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
        JPanel p = new JPanel();
        p.add(new JLabel("Hei"));
        return p;
    }

    private Component createOverview() {
        String overviewName = settings.getProperty("defaultOverview");
        return new OverviewHolder(overviewName, rootMap);
    }
}
