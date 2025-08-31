package wbe.demetersTeachings.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    private FileConfiguration config;

    public String noPermission;
    public String notEnoughArgs;
    public String reload;
    public String list;
    public String doubleDrop;
    public String hoeGiven;
    public String invalidMaterial;
    public String hoeArguments;
    public String foodGiven;
    public String foodArguments;
    public String foodChanceAdded;
    public String foodChanceArguments;
    public String doubleAdded;
    public String doubleArguments;
    public String modeChanged;
    public List<String> stats = new ArrayList<>();
    public List<String> help = new ArrayList<>();

    public Messages(FileConfiguration config) {
        this.config = config;

        noPermission = config.getString("Messages.noPermission").replace("&", "§");
        notEnoughArgs = config.getString("Messages.notEnoughArgs").replace("&", "§");
        reload = config.getString("Messages.reload").replace("&", "§");
        list = config.getString("Messages.list").replace("&", "§");
        doubleDrop = config.getString("Messages.doubleDrop").replace("&", "§");
        hoeGiven = config.getString("Messages.hoeGiven").replace("&", "§");
        invalidMaterial = config.getString("Messages.invalidMaterial").replace("&", "§");
        hoeArguments = config.getString("Messages.hoeArguments").replace("&", "§");
        foodGiven = config.getString("Messages.foodGiven").replace("&", "§");
        foodArguments = config.getString("Messages.foodArguments").replace("&", "§");
        foodChanceAdded = config.getString("Messages.foodChanceAdded").replace("&", "§");
        foodChanceArguments = config.getString("Messages.foodChanceArguments").replace("&", "§");
        doubleAdded = config.getString("Messages.doubleAdded").replace("&", "§");
        doubleArguments = config.getString("Messages.doubleArguments").replace("&", "§");
        modeChanged = config.getString("Messages.modeChanged").replace("&", "§");
        stats = config.getStringList("Messages.stats");
        help = config.getStringList("Messages.help");
    }
}
