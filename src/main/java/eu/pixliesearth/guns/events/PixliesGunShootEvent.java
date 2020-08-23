package eu.pixliesearth.guns.events;

import eu.pixliesearth.events.ShootEvent;
import eu.pixliesearth.guns.PixliesAmmo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public class PixliesGunShootEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();
    private Player shooter;
    private boolean cancelled;
    private PixliesAmmo ammo;

    public PixliesGunShootEvent(Player shooter, PixliesAmmo ammo) {
        this.shooter = shooter;
        this.ammo = ammo;
        this.cancelled = false;
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
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public Player getShooter() {
        return shooter;
    }

    public void setShooter(Player shooter) {
        this.shooter = shooter;
    }

    public PixliesAmmo getAmmo() {
        return ammo;
    }

    public void setAmmo(PixliesAmmo ammo) {
        this.ammo = ammo;
    }

}
