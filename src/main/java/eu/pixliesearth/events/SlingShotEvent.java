package eu.pixliesearth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SlingShotEvent extends Event implements Cancellable {
    public static HandlerList handlers = new HandlerList();
    Player shooter;
    boolean isCancelled = false;
    String name;


    public SlingShotEvent(Player player, String AmmoName) {
        this.shooter = player;
        this.name = AmmoName;
    }

    public Player getPlayer(){
        return shooter;
    }

    public String getAmmoName(){
        return name;
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
        this.isCancelled = b;
    }
}


