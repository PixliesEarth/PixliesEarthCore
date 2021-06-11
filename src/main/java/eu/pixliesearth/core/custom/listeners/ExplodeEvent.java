package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplodeEvent extends CustomListener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

}
