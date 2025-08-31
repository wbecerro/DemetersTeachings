package wbe.demetersTeachings.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.demetersTeachings.DemetersTeachings;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    private DemetersTeachings plugin;

    public Utilities() {
        plugin = DemetersTeachings.getInstance();
    }

    public void addFoodChance(ItemStack item, double chance) {
        NamespacedKey baseFoodKey = new NamespacedKey(plugin, "foodChance");
        String loreLine = DemetersTeachings.config.foodChance
                .replace("%food_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseFoodKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    public void addDoubleDropChance(ItemStack item, double chance) {
        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        String loreLine = DemetersTeachings.config.doubleChance
                .replace("%double_chance%", String.valueOf(chance));
        ItemMeta meta = item.getItemMeta();

        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        List<String> lore = new ArrayList<>();
        if(meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.add(loreLine);
        meta.setLore(lore);

        meta.getPersistentDataContainer().set(baseDoubleKey, PersistentDataType.DOUBLE, chance);
        item.setItemMeta(meta);
    }

    private double getItemFoodChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey baseFoodKey = new NamespacedKey(plugin, "foodChance");
        if(meta.getPersistentDataContainer().has(baseFoodKey)) {
            return meta.getPersistentDataContainer().get(baseFoodKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerFoodChance(Player player) {
        double chance = DemetersTeachings.config.baseFoodChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemFoodChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemFoodChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemFoodChance(item);
        }

        NamespacedKey baseFoodKey = new NamespacedKey(plugin, "foodChance");
        if(player.getPersistentDataContainer().has(baseFoodKey)) {
            chance += player.getPersistentDataContainer().get(baseFoodKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    private double getItemDoubleChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return 0;
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(meta.getPersistentDataContainer().has(baseDoubleKey)) {
            return meta.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return 0;
    }

    public double getPlayerDoubleChance(Player player) {
        double chance = DemetersTeachings.config.baseDoubleChance;

        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();
        ItemStack[] armor = inventory.getArmorContents();

        if(!mainHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(mainHand);
        }

        if(!offHand.getType().equals(Material.AIR)) {
            chance += getItemDoubleChance(offHand);
        }

        for(ItemStack item : armor) {
            if(item == null) {
                continue;
            }
            chance += getItemDoubleChance(item);
        }

        NamespacedKey baseDoubleKey = new NamespacedKey(plugin, "doubleChance");
        if(player.getPersistentDataContainer().has(baseDoubleKey)) {
            chance += player.getPersistentDataContainer().get(baseDoubleKey, PersistentDataType.DOUBLE);
        }

        return chance;
    }

    /**
     * Método para añadir probabilidad de comida o doble recompensas a un jugador.
     *
     * @param player Jugador al que añadir
     * @param chance Probabiliad a añadir
     * @param type Tipo de probabilidad 0 -> comida, 1 -> doble
     */
    public void addChanceToPlayer(Player player, double chance, int type) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "foodChance");
                break;
            default:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
        }

        if(player.getPersistentDataContainer().has(key)) {
            double playerChance = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
            playerChance += chance;
            player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, playerChance);
        } else {
            player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, chance);
        }
    }

    /**
     * Método para quitar probabilidad de comida o doble recompensas a un jugador.
     *
     * @param player Jugador al que quitar
     * @param chance Probabiliad a quitar
     * @param type Tipo de probabilidad 0 -> comida, 1 -> doble
     */
    public void removeChanceFromPlayer(Player player, double chance, int type) {
        NamespacedKey key;
        switch(type) {
            case 0:
                key = new NamespacedKey(plugin, "foodChance");
                break;
            default:
                key = new NamespacedKey(plugin, "doubleChance");
                break;
        }

        if(!player.getPersistentDataContainer().has(key)) {
            return;
        }

        double playerChance = player.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        playerChance -= chance;
        player.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, playerChance);
    }

    public void addItemToInventory(Player player, ItemStack item) {
        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(item);
        }
    }
}
