package eu.pixliesearth.events;

import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NationCreationEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();
    private @Getter Player player;
    private @Getter Nation nation;
    private boolean isCancelled = false;


    public NationCreationEvent(Player player, Nation nation) {
        this.player = player;
        this.nation = nation;
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

}
