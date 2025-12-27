package wbe.demetersTeachings.config;

import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;

public class Food {

    private String id;

    private Material material;

    private String name;

    private List<String> lore;

    private boolean glow;

    private int nutrition;

    private float saturation;

    private boolean canAlwaysEat;

    private ItemUseAnimation animation;

    private float consumeSeconds;

    private Sound sound = Sound.ENTITY_PLAYER_BURP;

    private List<FoodEffect> effects;

    public Food(String id, Material material, String name, List<String> lore, int nutrition, boolean glow,
                float saturation, boolean canAlwaysEat, ItemUseAnimation animation, float consumeSeconds) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.glow = glow;
        this.nutrition = nutrition;
        this.saturation = saturation;
        this.canAlwaysEat = canAlwaysEat;
        this.animation = animation;
        this.consumeSeconds = consumeSeconds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public int getNutrition() {
        return nutrition;
    }

    public void setNutrition(int nutrition) {
        this.nutrition = nutrition;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public boolean isCanAlwaysEat() {
        return canAlwaysEat;
    }

    public void setCanAlwaysEat(boolean canAlwaysEat) {
        this.canAlwaysEat = canAlwaysEat;
    }

    public ItemUseAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(ItemUseAnimation animation) {
        this.animation = animation;
    }

    public float getConsumeSeconds() {
        return consumeSeconds;
    }

    public void setConsumeSeconds(float consumeSeconds) {
        this.consumeSeconds = consumeSeconds;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public List<FoodEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<FoodEffect> effects) {
        this.effects = effects;
    }
}
