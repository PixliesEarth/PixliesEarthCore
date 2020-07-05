package eu.pixliesearth.events;

import eu.pixliesearth.nations.entities.nation.Nation;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GulagStartEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();
    private Player player;
    private Player enemy;
    private boolean isCancelled = false;


    public GulagStartEvent(Player player, Player enemy) {
        this.player = player;
        this.enemy = enemy;
    }


    public Player getPlayer(){
        return player;
    }

    public Player getEnemy(){
        return enemy;
    }

    @Override
    public HandlerList getHandlers(){
        return GulagStartEvent.handlers;
    }

    public static HandlerList getHandlerList(){
        return GulagStartEvent.handlers;
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