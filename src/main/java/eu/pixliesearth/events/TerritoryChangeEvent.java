package eu.pixliesearth.events;

import eu.pixliesearth.nations.entities.chunk.NationChunk;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TerritoryChangeEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();
    private @Getter Player shooter;
    private @Getter NationChunk chunk;
    private @Getter ChangeType type;
    private boolean isCancelled = false;


    public TerritoryChangeEvent(Player player, NationChunk chunk, ChangeType type) {
        this.shooter = player;
        this.chunk = chunk;
        this.type = type;
    }

    @Override
    public HandlerList getHandlers(){
        return ShootEvent.handlers;
    }

    public static HandlerList getHandlerList(){
        return ShootEvent.handlers;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = true;
    }

    public enum ChangeType {

        CLAIM_AUTO_SELF,
        CLAIM_ONE_SELF,
        CLAIM_FILL_SELF,
        CLAIM_AUTO_OTHER,
        CLAIM_ONE_OTHER,
        CLAIM_FILL_OTHER,
        UNCLAIM_AUTO_SELF,
        UNCLAIM_ONE_SELF,
        UNCLAIM_FILL_SELF,
        UNCLAIM_AUTO_OTHER,
        UNCLAIM_ONE_OTHER,
        UNCLAIM_FILL_OTHER

    }

}
