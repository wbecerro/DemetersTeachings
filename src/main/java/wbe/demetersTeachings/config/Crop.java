package wbe.demetersTeachings.config;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Crop {

    private Material id;

    private HashMap<Food, Double> foods;

    private double maxWeight;

    public Crop(Material id, HashMap<Food, Double> foods) {
        this.id = id;
        this.foods = foods;

        maxWeight = 0;
        for(Double weight : foods.values()) {
            maxWeight += weight;
        }
    }

    public Material getId() {
        return id;
    }

    public void setId(Material id) {
        this.id = id;
    }

    public HashMap<Food, Double> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Food, Double> foods) {
        this.foods = foods;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public Food getRandomReward() {
        Random random = new Random();
        double randomNumber = random.nextDouble(maxWeight);
        double weight = 0;
        Map.Entry<Food, Double> last = null;

        for(Map.Entry<Food, Double> reward : foods.entrySet()) {
            weight += reward.getValue();
            if(randomNumber < weight) {
                return reward.getKey();
            }
            last = reward;
        }

        return last.getKey();
    }
}
