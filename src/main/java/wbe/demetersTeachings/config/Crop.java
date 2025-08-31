package wbe.demetersTeachings.config;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Crop {

    private Material id;

    private HashMap<Double, Food> foods;

    private double maxWeight;

    public Crop(Material id, HashMap<Double, Food> foods) {
        this.id = id;
        this.foods = foods;

        maxWeight = 0;
        for(Double weight : foods.keySet()) {
            maxWeight += weight;
        }
    }

    public Material getId() {
        return id;
    }

    public void setId(Material id) {
        this.id = id;
    }

    public HashMap<Double, Food> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Double, Food> foods) {
        this.foods = foods;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public Food getRandomReward() {
        Random random = new Random();
        double randomNumber = random.nextDouble(maxWeight);
        double weight = 0;
        Map.Entry<Double, Food> last = null;

        for(Map.Entry<Double, Food> reward : foods.entrySet()) {
            weight += reward.getKey();
            if(randomNumber < weight) {
                return reward.getValue();
            }
            last = reward;
        }

        return last.getValue();
    }
}
