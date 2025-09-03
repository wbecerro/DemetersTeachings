package wbe.demetersTeachings.config;

import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Config {

    private FileConfiguration config;

    public double baseFoodChance;
    public double baseDoubleChance;

    public Sound doubleDropSound;
    public Sound foodDropSound;
    public Sound changeModeSound;

    public String hoeName;
    public List<String> hoeLore;
    public String foodChance;
    public String doubleChance;
    public List<String> foodStats;
    public String noEffects;
    public String foodEffect;

    public HashMap<String, Food> foods = new HashMap<>();
    public HashMap<Material, Crop> harvestCrops = new HashMap<>();
    public HashMap<Material, Crop> breakCrops = new HashMap<>();
    public HashMap<Material, Crop> checkBreakCrops = new HashMap<>();

    public Config(FileConfiguration config) {
        this.config = config;

        baseFoodChance = config.getDouble("Config.baseFoodChance");
        baseDoubleChance = config.getDouble("Config.baseDoubleChance");

        doubleDropSound = Registry.SOUNDS.get(NamespacedKey.minecraft(config.getString("Sounds.doubleDropSound").toLowerCase()));
        foodDropSound = Registry.SOUNDS.get(NamespacedKey.minecraft(config.getString("Sounds.foodDropSound").toLowerCase()));
        changeModeSound = Registry.SOUNDS.get(NamespacedKey.minecraft(config.getString("Sounds.changeModeSound").toLowerCase()));

        hoeName = config.getString("Items.hoe.name").replace("&", "§");
        hoeLore = config.getStringList("Items.hoe.lore");
        foodChance = config.getString("Items.hoe.foodChance").replace("&", "§");
        doubleChance = config.getString("Items.hoe.doubleChance").replace("&", "§");
        noEffects = config.getString("Items.noEffects").replace("&", "§");
        foodEffect = config.getString("Items.foodEffect").replace("&", "§");
        foodStats = config.getStringList("Items.foodStats");

        loadFoods();
        loadHarvestCrops();
        loadBreakCrops();
        loadCheckBreakCrops();
    }

    private void loadFoods() {
        Set<String> configFoods = config.getConfigurationSection("Foods").getKeys(false);
        for(String configFood : configFoods) {
            Material material = Material.valueOf(config.getString("Foods." + configFood + ".material"));
            String name = config.getString("Foods." + configFood + ".name").replace("&", "§");
            List<String> lore = config.getStringList("Foods." + configFood + ".lore");
            boolean glow = config.getBoolean("Foods." + configFood + ".glow");
            int nutrition = config.getInt("Foods." + configFood + ".nutrition");
            float saturation = (float) config.getDouble("Foods." + configFood + ".saturation");
            boolean canAlwaysEat = config.getBoolean("Foods." + configFood + ".canAlwaysEat");
            ItemUseAnimation animation = ItemUseAnimation.valueOf(config.getString("Foods." + configFood + ".animation"));
            float consumeSeconds = (float) config.getDouble("Foods." + configFood + ".consumeSeconds");
            Food food = new Food(configFood, material, name, lore, nutrition, glow, saturation, canAlwaysEat, animation, consumeSeconds);
            if(config.contains("Foods." + configFood + ".sound")) {
                food.setSound(Registry.SOUNDS.get(NamespacedKey.minecraft(config.getString("Foods." + configFood + ".sound").toLowerCase())));
            }

            if(config.contains("Foods." + configFood + ".effects")) {
                Set<String> effects = config.getConfigurationSection("Foods." + configFood + ".effects").getKeys(false);
                HashMap<PotionEffect, Double> potionEffects = new HashMap<>();
                for(String effect : effects) {
                    int level = config.getInt("Foods." + configFood + ".effects." + effect + ".level") - 1;
                    int duration = config.getInt("Foods." + configFood + ".effects." + effect + ".duration") * 20;
                    double chance = config.getDouble(("Foods." + configFood + ".effects." + effect + ".chance")) / 100;
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(effect.toUpperCase()), duration, level);
                    potionEffects.put(potionEffect, chance);
                }

                food.setEffects(potionEffects);
            }

            foods.put(configFood, food);
        }
    }

    private void loadHarvestCrops() {
        Set<String> configCrops = config.getConfigurationSection("Crops.harvest").getKeys(false);
        for(String crop : configCrops) {
            Material id = Material.valueOf(crop);
            HashMap<Double, Food> foods = loadFoodRewards("Crops.harvest." + crop + ".foods");
            harvestCrops.put(id, new Crop(id, foods));
        }
    }

    private void loadBreakCrops() {
        Set<String> configCrops = config.getConfigurationSection("Crops.break").getKeys(false);
        for(String crop : configCrops) {
            Material id = Material.valueOf(crop);
            HashMap<Double, Food> foods = loadFoodRewards("Crops.break." + crop + ".foods");
            breakCrops.put(id, new Crop(id, foods));
        }
    }

    private void loadCheckBreakCrops() {
        Set<String> configCrops = config.getConfigurationSection("Crops.checkBreak").getKeys(false);
        for(String crop : configCrops) {
            Material id = Material.valueOf(crop);
            HashMap<Double, Food> foods = loadFoodRewards("Crops.checkBreak." + crop + ".foods");
            checkBreakCrops.put(id, new Crop(id, foods));
        }
    }

    private HashMap<Double, Food> loadFoodRewards(String path) {
        Set<String> configRewards = config.getConfigurationSection(path).getKeys(false);
        HashMap<Double, Food> rewards = new HashMap<>();
        for(String reward : configRewards) {
            Food food = foods.get(reward);
            double weight = config.getDouble(path + "." + reward + ".weight");
            rewards.put(weight, food);
        }

        return rewards;
    }
}
