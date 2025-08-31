package wbe.demetersTeachings.listeners;

import org.bukkit.plugin.PluginManager;
import wbe.demetersTeachings.DemetersTeachings;

public class EventListeners {

    public void initializeListeners() {
        DemetersTeachings plugin = DemetersTeachings.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListeners(), plugin);
        pluginManager.registerEvents(new PlayerDropItemListeners(), plugin);
        pluginManager.registerEvents(new PlayerHarvestBlockListeners(), plugin);
    }
}
