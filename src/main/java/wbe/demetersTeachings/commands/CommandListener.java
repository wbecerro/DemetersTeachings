package wbe.demetersTeachings.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wbe.demetersTeachings.DemetersTeachings;
import wbe.demetersTeachings.config.Food;
import wbe.demetersTeachings.items.FoodItem;
import wbe.demetersTeachings.items.Hoe;

public class CommandListener implements CommandExecutor {

    private DemetersTeachings plugin;

    public CommandListener() {
        plugin = DemetersTeachings.getInstance();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("DemetersTeachings")) {
            Player player = null;
            if(sender instanceof Player) {
                player = (Player) sender;
            }

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if(!sender.hasPermission("demetersteachings.command.help")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                for(String line : DemetersTeachings.messages.help) {
                    sender.sendMessage(line.replace("&", "§"));
                }
            } else if(args[0].equalsIgnoreCase("stats")) {
                if(!sender.hasPermission("demetersteachings.command.stats")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                String foodChance = String.valueOf(DemetersTeachings.utilities.getPlayerFoodChance(player));
                String doubleChance = String.valueOf(DemetersTeachings.utilities.getPlayerDoubleChance(player));
                for(String line : DemetersTeachings.messages.stats) {
                    sender.sendMessage(line.replace("&", "§")
                            .replace("%foodChance%", foodChance)
                            .replace("%doubleChance%", doubleChance));
                }
            } else if(args[0].equalsIgnoreCase("hoe")) {
                if(!sender.hasPermission("demetersteachings.command.hoe")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                if(args.length < 4) {
                    sender.sendMessage(DemetersTeachings.messages.notEnoughArgs);
                    sender.sendMessage(DemetersTeachings.messages.hoeArguments);
                    return false;
                }

                Material material;
                try {
                    material = Material.valueOf(args[1].toUpperCase());
                } catch(IllegalArgumentException ex) {
                    sender.sendMessage(DemetersTeachings.messages.invalidMaterial);
                    return false;
                }

                String altMaterial = args[2].toUpperCase();
                double foodChance = Double.valueOf(args[3]);

                Hoe hoe = new Hoe(material, altMaterial, foodChance);
                if(args.length > 4) {
                    player = Bukkit.getPlayer(args[4]);
                }

                DemetersTeachings.utilities.addItemToInventory(player, hoe);
                player.sendMessage(DemetersTeachings.messages.hoeGiven);
            } else if(args[0].equalsIgnoreCase("list")) {
                if(!sender.hasPermission("demetersteachings.command.list")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                sender.sendMessage(DemetersTeachings.messages.list + DemetersTeachings.config.foods.values().toString());
            } else if(args[0].equalsIgnoreCase("food")) {
                if(!sender.hasPermission("demetersteachings.command.food")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(DemetersTeachings.messages.notEnoughArgs);
                    sender.sendMessage(DemetersTeachings.messages.foodArguments);
                    return false;
                }

                Food food = DemetersTeachings.config.foods.get(args[1]);
                FoodItem foodItem = new FoodItem(food);
                if(args.length > 2) {
                    player = Bukkit.getPlayer(args[2]);
                }

                DemetersTeachings.utilities.addItemToInventory(player, foodItem);
                player.sendMessage(DemetersTeachings.messages.foodGiven.replace("%food%", food.getName()));
            } else if(args[0].equalsIgnoreCase("foodChance")) {
                if(!sender.hasPermission("demetersteachings.command.foodChance")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(DemetersTeachings.messages.notEnoughArgs);
                    sender.sendMessage(DemetersTeachings.messages.foodChanceArguments);
                    return false;
                }

                DemetersTeachings.utilities.addFoodChance(player.getInventory().getItemInMainHand(), Double.valueOf(args[1]));
                player.sendMessage(DemetersTeachings.messages.foodChanceAdded);
            } else if(args[0].equalsIgnoreCase("double")) {
                if(!sender.hasPermission("demetersteachings.command.double")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                if(args.length < 2) {
                    sender.sendMessage(DemetersTeachings.messages.notEnoughArgs);
                    sender.sendMessage(DemetersTeachings.messages.doubleArguments);
                    return false;
                }

                DemetersTeachings.utilities.addDoubleDropChance(player.getInventory().getItemInMainHand(), Double.valueOf(args[1]));
                player.sendMessage(DemetersTeachings.messages.doubleAdded);
            } else if(args[0].equalsIgnoreCase("reload")) {
                if(!sender.hasPermission("demetersteachings.command.reload")) {
                    sender.sendMessage(DemetersTeachings.messages.noPermission);
                    return false;
                }

                plugin.reloadConfiguration();
                sender.sendMessage(DemetersTeachings.messages.reload);
            }
        }
        return true;
    }
}
