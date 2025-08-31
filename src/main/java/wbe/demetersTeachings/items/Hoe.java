package wbe.demetersTeachings.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wbe.demetersTeachings.DemetersTeachings;

import java.util.ArrayList;

public class Hoe extends ItemStack {

    public Hoe(Material material, String materialAlt, double foodChance) {
        super(material);

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(material);
        }

        meta.setDisplayName(DemetersTeachings.config.hoeName);

        ArrayList<String> lore = new ArrayList<>();
        for(String line : DemetersTeachings.config.hoeLore) {
            lore.add(line.replace("&", "§"));
        }

        lore.add(DemetersTeachings.config.foodChance.replace("%food_chance%", String.valueOf(foodChance)));
        meta.setLore(lore);

        NamespacedKey foodKey = new NamespacedKey(DemetersTeachings.getInstance(), "foodChance");
        meta.getPersistentDataContainer().set(foodKey, PersistentDataType.DOUBLE, foodChance);
        NamespacedKey altMaterialKey = new NamespacedKey(DemetersTeachings.getInstance(), "altMaterial");
        meta.getPersistentDataContainer().set(altMaterialKey, PersistentDataType.STRING, materialAlt);

        setItemMeta(meta);
    }
}
