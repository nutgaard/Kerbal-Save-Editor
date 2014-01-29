package no.utgdev.kerbal;

import com.alee.laf.WebLookAndFeel;
import no.utgdev.kerbal.io.SavefileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.Policy;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.addpluginsfrom.OptionReportAfter;
import net.xeoh.plugins.base.util.JSPFProperties;
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

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {       
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());
        
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("This is a message from logback");
        logger.error("Fuck off");
        
        final JSPFProperties props = new JSPFProperties();
        props.setProperty("net.xeoh.plugins.base.PluginManager.logging.level", "INFO");
        
        PluginManager pmf = PluginManagerFactory.createPluginManager(props);
        pmf.addPluginsFrom(new File("./").toURI(), new OptionReportAfter());

        PluginManagerUtil pmu = new PluginManagerUtil(pmf);

        Class<? extends NamedPlugin>[] pluginClasses = new Class[]{
            NamedPlugin.class,
            ViewPlugin.class,
            OverviewPlugin.class,
            OverviewContextMenuPlugin.class
        };
        for (Class<? extends NamedPlugin> pluginCls : pluginClasses) {
            System.out.print("Seaching for: "+pluginCls);
            PluginCache.create(pluginCls, (Collection) pmu.getPlugins(pluginCls));
            System.out.println(" found: " + PluginCache.getInstance(pluginCls).getList().size());
            for (Plugin p : PluginCache.getInstance(pluginCls).getList()) {
                System.out.println("    "+p);
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
