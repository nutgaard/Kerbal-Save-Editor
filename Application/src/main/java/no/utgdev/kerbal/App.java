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
import net.xeoh.plugins.base.util.PluginManagerUtil;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.io.SavefileCreator;
import no.utgdev.kerbal.io.SavefileParser;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.mvp.MainFrame;
import no.utgdev.kerbal.plugin.PluginCache;
import no.utgdev.kerbal.security.PluginPolicy;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());

        PluginManager pmf = PluginManagerFactory.createPluginManager();
        pmf.addPluginsFrom(new File("./").toURI());

        PluginManagerUtil pmu = new PluginManagerUtil(pmf);

        Class<? extends Plugin>[] pluginClasses = new Class[]{
            ViewPlugin.class,
            OverviewPlugin.class
        };
        for (Class<? extends Plugin> pluginCls : pluginClasses) {
            System.out.print("Seaching for: "+pluginCls);
            PluginCache.create(pluginCls, (Collection) pmu.getPlugins(pluginCls));
            System.out.println(" found: " + PluginCache.getInstance(pluginCls).getList().size());
        }
        
        
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
