package wbe.demetersTeachings.config;

import org.bukkit.potion.PotionEffect;

public class FoodEffect {

    private PotionEffect effect;

    private double chance;

    public FoodEffect(PotionEffect effect, double chance) {
        this.effect = effect;
        this.chance = chance;
    }

    public PotionEffect getEffect() {
        return effect;
    }

    public void setEffect(PotionEffect effect) {
        this.effect = effect;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }
}
