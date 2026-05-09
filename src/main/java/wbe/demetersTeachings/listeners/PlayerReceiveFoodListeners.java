package wbe.demetersTeachings.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.config.Crop;
import wbe.demetersTeachings.events.PlayerReceiveFoodEvent;
import wbe.demetersTeachings.items.FoodItem;

import java.util.Random;

public class PlayerReceiveFoodListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void manageFoodDistribution(PlayerReceiveFoodEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Random random = new Random();
        double doubleChance = DemetersTeachings.utilities.getPlayerDoubleChance(player);
        Crop crop = event.getCrop();

        player.playSound(player.getLocation(), DemetersTeachings.config.foodDropSound, 1F, 1F);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new FoodItem(crop.getRandomReward()));
        if(random.nextDouble(100) <= doubleChance) {
            player.sendMessage(DemetersTeachings.messages.doubleDrop);
            player.playSound(player.getLocation(), DemetersTeachings.config.doubleDropSound, 1F, 1F);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new FoodItem(crop.getRandomReward()));
        }
    }
}
