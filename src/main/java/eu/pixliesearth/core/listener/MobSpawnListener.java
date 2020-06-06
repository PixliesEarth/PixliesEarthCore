package eu.pixliesearth.core.listener;

import org.bukkit.entity.Phantom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnListener implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event instanceof Phantom)
            event.setCancelled(true);
    }

}
