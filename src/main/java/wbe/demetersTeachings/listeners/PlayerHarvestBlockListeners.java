package wbe.demetersTeachings.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.config.Crop;
import wbe.demetersTeachings.events.PlayerReceiveFoodEvent;

import java.util.Random;

public class PlayerHarvestBlockListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleHarvestFoodGeneration(PlayerHarvestBlockEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Material harvested = event.getHarvestedBlock().getType();
        if(!DemetersTeachings.config.harvestCrops.keySet().contains(harvested)) {
            return;
        }

        Crop crop = DemetersTeachings.config.harvestCrops.get(harvested);
        Player player = event.getPlayer();
        double foodChance = DemetersTeachings.utilities.getPlayerFoodChance(player);
        Random random = new Random();

        if(random.nextDouble(100) <= foodChance) {
            DemetersTeachings.getInstance().getServer().getPluginManager().callEvent(new PlayerReceiveFoodEvent(player, crop, event.getHarvestedBlock()));
        }
    }
}
