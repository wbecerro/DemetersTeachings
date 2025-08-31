package wbe.demetersTeachings.listeners;

import com.gmail.nossr50.mcMMO;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.config.Crop;
import wbe.demetersTeachings.items.FoodItem;

import java.util.List;
import java.util.Random;

public class BlockBreakListeners implements Listener {

    private final List<Material> notAgeableSuitable = List.of(Material.SUGAR_CANE, Material.CACTUS, Material.BAMBOO);

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleBlockBreakFoodGeneration(BlockBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }

        Material broken = event.getBlock().getType();
        Crop crop;
        if(DemetersTeachings.config.breakCrops.keySet().contains(broken)) {
            crop = DemetersTeachings.config.breakCrops.get(broken);
        } else if(DemetersTeachings.config.checkBreakCrops.keySet().contains(broken)) {
            if(Bukkit.getPluginManager().getPlugin("mcMMO") == null) {
                return;
            }

            if(mcMMO.getUserBlockTracker().isIneligible(event.getBlock().getState())) {
                return;
            }

            crop = DemetersTeachings.config.checkBreakCrops.get(broken);
        } else {
            return;
        }

        if(event.getBlock().getBlockData() instanceof Ageable ageable && !notAgeableSuitable.contains(broken)) {
            if(ageable.getAge() < ageable.getMaximumAge()) {
                return;
            }
        }

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
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), foodItem);
        }
    }
}
