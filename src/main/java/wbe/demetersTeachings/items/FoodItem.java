package wbe.demetersTeachings.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.consumable.ConsumeEffect;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.config.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodItem extends ItemStack {

    public FoodItem(Food food) {
        super(food.getMaterial());

        ItemMeta meta;
        if(hasItemMeta()) {
            meta = getItemMeta();
        } else {
            meta = Bukkit.getItemFactory().getItemMeta(food.getMaterial());
        }

        meta.setDisplayName(food.getName());

        ArrayList<String> lore = new ArrayList<>();
        for(String line : food.getLore()) {
            lore.add(line.replace("&", "§"));
        }

        for(String line : DemetersTeachings.config.foodStats) {
            lore.add(line.replace("&", "§")
                    .replace("%food%", String.valueOf(food.getNutrition()))
                    .replace("%saturation%", String.valueOf(food.getSaturation()))
                    .replace("%consume%", String.valueOf(food.getConsumeSeconds())));
        }

        Consumable.Builder builder = Consumable.consumable();
        builder.animation(food.getAnimation()).consumeSeconds(food.getConsumeSeconds())
                .sound(Registry.SOUNDS.getKey(food.getSound()));

        if(food.getEffects() != null && !food.getEffects().isEmpty()) {
            for(Map.Entry<PotionEffect, Double> effect : food.getEffects().entrySet()) {
                ConsumeEffect consumableEffect = ConsumeEffect.applyStatusEffects(List.of(effect.getKey()), effect.getValue().floatValue());

                builder.addEffect(consumableEffect);
                PotionEffect potion = effect.getKey();
                lore.add(DemetersTeachings.config.foodEffect.replace("%effect%", potion.getType().getName())
                        .replace("%level%", String.valueOf(potion.getAmplifier() + 1))
                        .replace("%duration%", String.valueOf((int) potion.getDuration() / 20))
                        .replace("%chance%", String.valueOf(effect.getValue() * 100)));
            }

        } else {
            lore.add(DemetersTeachings.config.noEffects);
        }

        meta.setLore(lore);

        NamespacedKey effectivenessKey = new NamespacedKey(DemetersTeachings.getInstance(), "food");
        meta.getPersistentDataContainer().set(effectivenessKey, PersistentDataType.BOOLEAN, true);

        if(food.isGlow()) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        FoodComponent foodComponent = meta.getFood();

        foodComponent.setNutrition(food.getNutrition());
        foodComponent.setSaturation(food.getSaturation());
        foodComponent.setCanAlwaysEat(food.isCanAlwaysEat());
        meta.setFood(foodComponent);
        setItemMeta(meta);

        this.setData(DataComponentTypes.CONSUMABLE, builder.build());
    }
}
