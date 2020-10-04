package eu.pixliesearth.core.listener;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Restrictions implements Listener {

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntity() instanceof Creeper || event.getEntity() instanceof TNTPrimed)
            event.setCancelled(true);
    }

}
