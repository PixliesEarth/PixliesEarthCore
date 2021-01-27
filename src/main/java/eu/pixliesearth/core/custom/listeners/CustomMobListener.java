package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.mobs.PixliesWolf;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class CustomMobListener extends CustomListener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Monster) {
            double d = Math.random();
            if (d < 0.5) {
                event.getEntity().remove();
                new PixliesWolf(event.getLocation());
            }
        }
    }

}
