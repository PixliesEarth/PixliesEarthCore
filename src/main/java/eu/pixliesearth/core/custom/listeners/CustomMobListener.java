package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.mobs.PixliesWolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class CustomMobListener extends CustomListener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        double d = Math.random();
        if (d < 0.5) {
            new PixliesWolf(event.getLocation());
            event.getEntity().remove();
        }
    }

}
