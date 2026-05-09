package wbe.demetersTeachings.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import wbe.demetersTeachings.config.Crop;
import wbe.demetersTeachings.config.Food;

public class PlayerReceiveFoodEvent extends Event implements Cancellable {

    private Player player;

    private Block block;

    private Crop crop;

    private boolean isCancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public PlayerReceiveFoodEvent(Player player, Crop crop, Block block) {
        this.player = player;
        this.crop = crop;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
