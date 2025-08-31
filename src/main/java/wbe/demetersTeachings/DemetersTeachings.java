package wbe.demetersTeachings;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wbe.demetersTeachings.commands.CommandListener;
import wbe.demetersTeachings.commands.TabListener;
import wbe.demetersTeachings.config.Config;
import wbe.demetersTeachings.config.Messages;
import wbe.demetersTeachings.listeners.EventListeners;
import wbe.demetersTeachings.papi.PapiExtension;
import wbe.demetersTeachings.utils.Utilities;

import java.io.File;

public final class DemetersTeachings extends JavaPlugin {

    private FileConfiguration configuration;

    private CommandListener commandListener;

    private TabListener tabListener;

    private EventListeners eventListeners;

    private PapiExtension papiExtension;

    public static Config config;

    public static Messages messages;

    public static Utilities utilities;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiExtension = new PapiExtension();
            papiExtension.register();
        }
        saveDefaultConfig();
        getLogger().info("Demeter's Teachings enabled correctly.");
        reloadConfiguration();

        commandListener = new CommandListener();
        getCommand("demetersteachings").setExecutor(commandListener);
        tabListener = new TabListener();
        getCommand("demetersteachings").setTabCompleter(tabListener);
        eventListeners = new EventListeners();
        eventListeners.initializeListeners();
    }

    @Override
    public void onDisable() {
        reloadConfig();
        getLogger().info("Demeter's Teachings disabled correctly.");
    }

    public static DemetersTeachings getInstance() {
        return getPlugin(DemetersTeachings.class);
    }

    public void reloadConfiguration() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        reloadConfig();
        configuration = getConfig();
        config = new Config(configuration);
        messages = new Messages(configuration);
        utilities = new Utilities();
    }
}
