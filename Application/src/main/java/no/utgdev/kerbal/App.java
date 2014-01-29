package no.utgdev.kerbal;

import com.alee.laf.WebLookAndFeel;
import no.utgdev.kerbal.io.SavefileReader;
import java.io.File;
import java.security.Policy;
import java.util.Collection;
import java.util.List;
import javax.swing.SwingUtilities;
import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.addpluginsfrom.OptionReportAfter;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import no.utgdev.kerbal.common.i18n.I18n;
import no.utgdev.kerbal.common.plugin.NamedPlugin;
import no.utgdev.kerbal.common.plugin.OverviewContextMenuPlugin;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.io.SavefileCreator;
import no.utgdev.kerbal.io.SavefileParser;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.mvp.MainFrame;
import no.utgdev.kerbal.plugin.PluginCache;
import no.utgdev.kerbal.security.PluginPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * Hello world!
 *
 */
public class App {
    public static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("Starting application.");
        logger.debug("Setting new policy and security manager.");
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());
        
        logger.debug("Rewriting System.out and System.err to utilize slf4j.");
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        
        File currentLocation = new File("./");
        logger.info("Loading plugins from {}", currentLocation.toURI());
        PluginManager pmf = PluginManagerFactory.createPluginManager();
        pmf.addPluginsFrom(currentLocation.toURI(), new OptionReportAfter());

        PluginManagerUtil pmu = new PluginManagerUtil(pmf);

        Class<? extends NamedPlugin>[] pluginClasses = new Class[]{
            NamedPlugin.class,
            ViewPlugin.class,
            OverviewPlugin.class,
            OverviewContextMenuPlugin.class
        };
        for (Class<? extends NamedPlugin> pluginCls : pluginClasses) {
            logger.info("Seaching for: {}", pluginCls);
            Collection plugins = pmu.getPlugins(pluginCls);
            PluginCache.create(pluginCls, plugins);
            logger.info("Found {} matches for {}", plugins.size(), pluginCls);
            for (Plugin p : PluginCache.getInstance(pluginCls).getList()) {
                logger.debug("  {}", p);
            }
        }
        I18n.initiate(PluginCache.getInstance(NamedPlugin.class).getList());
        
        File from = new File("./quicksave.sfs");
        List<String> content = SavefileReader.read(from);
        SavefileParser parser = new SavefileParser(from.getName(), content);
        final PropertyMap root = parser.parse();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WebLookAndFeel.install();
                new MainFrame(root);
            }
        });

    }

    public static void tests() {
        File from = new File("G:\\Steam\\steamapps\\common\\Kerbal Space Program\\saves\\WMods\\quicksave.sfs");
        File to = new File("G:\\Steam\\steamapps\\common\\Kerbal Space Program\\saves\\WMods\\recreated.sfs");
        long start = System.currentTimeMillis();
        List<String> content = SavefileReader.read(from);
        SavefileParser parser = new SavefileParser(from.getName(), content);
        PropertyMap root = parser.parse();
        System.out.println("Time to read/parse/lex: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();

        SavefileCreator.write(to, root);
        System.out.println("Time to recreate: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        FileAcceptance.verify(from, to);
        System.out.println("Verification: " + (System.currentTimeMillis() - start));
    }
}
