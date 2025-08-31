package wbe.demetersTeachings.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.config.Crop;
import wbe.demetersTeachings.items.FoodItem;

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
        double doubleChance = DemetersTeachings.utilities.getPlayerDoubleChance(player);

        Random random = new Random();
        int rewardsAmount = 0;
        if(random.nextDouble(100) <= foodChance) {
            player.playSound(player.getLocation(), DemetersTeachings.config.foodDropSound, 1F, 1F);
            rewardsAmount++;
            if(random.nextDouble(100) <= doubleChance) {
                player.sendMessage(DemetersTeachings.messages.doubleDrop);
                player.playSound(player.getLocation(), DemetersTeachings.config.doubleDropSound, 1F, 1F);
                rewardsAmount++;
            }
        }

        for(int i=0;i<rewardsAmount;i++) {
            FoodItem foodItem = new FoodItem(crop.getRandomReward());
            event.getHarvestedBlock().getWorld().dropItemNaturally(event.getHarvestedBlock().getLocation(), foodItem);
        }
    }
}
