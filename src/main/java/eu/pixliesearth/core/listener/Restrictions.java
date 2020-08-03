package eu.pixliesearth.core.listener;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class Restrictions implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event instanceof Phantom)
            event.setCancelled(true);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper)
            event.setCancelled(true);
        if (event.getEntity() instanceof TNTPrimed)
            event.setCancelled(true);
    }

    @EventHandler
    public void manipulate(PlayerArmorStandManipulateEvent e)
    {
        if(!e.getRightClicked().isVisible())
        {
            e.setCancelled(true);
        }
    }

}
