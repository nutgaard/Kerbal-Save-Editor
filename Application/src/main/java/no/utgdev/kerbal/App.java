package no.utgdev.kerbal;

import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
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
import no.utgdev.kerbal.common.Settings;
import no.utgdev.kerbal.common.i18n.I18N;
import no.utgdev.kerbal.common.plugin.NamedPlugin;
import no.utgdev.kerbal.common.plugin.OverviewContextMenuPlugin;
import no.utgdev.kerbal.common.plugin.OverviewPlugin;
import no.utgdev.kerbal.common.plugin.ViewPlugin;
import no.utgdev.kerbal.io.SavefileCreator;
import no.utgdev.kerbal.io.SavefileParser;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.common.treemodel.marshallingObjects.Savefile;
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

    public static Logger logger;

    public static void main(String[] args) {
        securityConfiguration();

        //Logger must be initiated after security configuration.
        logger = LoggerFactory.getLogger(App.class);
        logger.info("Starting application.");

        logger.debug("Rewriting System.out and System.err to utilize slf4j.");
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        
        Settings.getInstance();
        
        configureAppenders((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME));

        loadPluginsFrom(new File("./"));

        I18N.initiate(PluginCache.getInstance(NamedPlugin.class).getList());

        
        final Savefile savefile = loadPreviousOpenFile(new File(Settings.getInstance().getProperty("file.open")));
        
        logger.info("Initial setup completed, starting GUI.");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WebLookAndFeel.install();
                MainFrame mf = new MainFrame(savefile);
            }
        });
        logger.info("Its complete");
    }

    public static void tests() {
        File from = new File("G:\\Steam\\steamapps\\common\\Kerbal Space Program\\saves\\WMods\\quicksave.sfs");
        File to = new File("G:\\Steam\\steamapps\\common\\Kerbal Space Program\\saves\\WMods\\recreated.sfs");
        long start = System.currentTimeMillis();
        List<String> content = SavefileReader.read(from);
        SavefileParser parser = new SavefileParser(from.getName(), content);
        PropertyMap root = parser.parse();
        logger.debug("Time to read/parse/lex: {}", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();

        SavefileCreator.write(to, root);
        logger.debug("Time to recreate: {}", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        FileAcceptance.verify(from, to);
        logger.debug("Verification: {}", System.currentTimeMillis() - start);
    }

    private static void securityConfiguration() {
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());
    }

    private static void configureAppenders(ch.qos.logback.classic.Logger rootLogger) {
        logger.info("Applying settings configuration to appenders");
        
        Settings settings = Settings.getInstance();
        
        UnsynchronizedAppenderBase stdoutAppender = (UnsynchronizedAppenderBase) rootLogger.getAppender("STDOUT");
        ThresholdFilter stdoutFilter = (ThresholdFilter) stdoutAppender.getCopyOfAttachedFiltersList().get(0);
        stdoutFilter.setLevel(settings.getProperty("logging.stdout.level"));

        UnsynchronizedAppenderBase fileAppender = (UnsynchronizedAppenderBase) rootLogger.getAppender("FILE");
        ThresholdFilter fileFilter = (ThresholdFilter) fileAppender.getCopyOfAttachedFiltersList().get(0);
        fileFilter.setLevel(settings.getProperty("logging.file.level"));
        logger.info("Configuration of appenders complete.");
    }

    private static void loadPluginsFrom(File file) {
        logger.info("Loading plugins from {}", file.toURI());
        
        PluginManager pmf = PluginManagerFactory.createPluginManager();
        pmf.addPluginsFrom(file.toURI(), new OptionReportAfter());
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
        logger.info("Loading of plugins complete.");
    }

    private static Savefile loadPreviousOpenFile(File file) {
        if (!file.exists()){
            return new Savefile(I18N.getInstance().getString("nofile"));
        }
        
        List<String> content = SavefileReader.read(file);
        SavefileParser parser = new SavefileParser(file.getName(), content);
        return parser.parse();
    }
}
