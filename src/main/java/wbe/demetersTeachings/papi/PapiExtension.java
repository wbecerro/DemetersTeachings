package wbe.demetersTeachings.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import wbe.demetersTeachings.DemetersTeachings;

public class PapiExtension extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "wbe";
    }

    @Override
    public String getIdentifier() {
        return "DemetersTeachings";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("foodChance")) {
            return String.valueOf(DemetersTeachings.utilities.getPlayerFoodChance(player.getPlayer()));
        } else if(params.equalsIgnoreCase("doubleChance")) {
            return String.valueOf(DemetersTeachings.utilities.getPlayerDoubleChance(player.getPlayer()));
        }

        return null;
    }
}
