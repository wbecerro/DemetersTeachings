package wbe.demetersTeachings.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.demetersTeachings.DemetersTeachings;

public class PlayerDropItemListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void switchModeOnDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) {
            return;
        }

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if(droppedItem.getType().equals(Material.AIR)) {
            return;
        }

        ItemMeta meta = droppedItem.getItemMeta();
        if(meta == null) {
            return;
        }

        NamespacedKey altMaterialKey = new NamespacedKey(DemetersTeachings.getInstance(), "altMaterial");
        if(!meta.getPersistentDataContainer().has(altMaterialKey)) {
            return;
        }

        Material altMaterial = Material.valueOf(meta.getPersistentDataContainer().get(altMaterialKey, PersistentDataType.STRING));
        String oldMaterial = droppedItem.getType().toString();
        droppedItem.setType(altMaterial);
        meta.getPersistentDataContainer().set(altMaterialKey, PersistentDataType.STRING, oldMaterial);
        droppedItem.setItemMeta(meta);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(DemetersTeachings.messages.modeChanged));
        player.playSound(player.getLocation(), DemetersTeachings.config.changeModeSound, 1F, 1F);
        event.setCancelled(true);
    }

}
