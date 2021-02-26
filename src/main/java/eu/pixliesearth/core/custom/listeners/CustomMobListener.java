package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomMob;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class CustomMobListener extends CustomListener {

    @SneakyThrows
    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Player) return;
        if (!(event.getEntity() instanceof Damageable)) return;
        Damageable damageable = (Damageable) event.getEntity();
        if (event.getEntity() instanceof Monster) {
            double d = Math.random();
            for (CustomMob mob : CustomMob.values()) {
                if (mob.getBiomes().contains(event.getLocation().getBlock().getBiome()) && mob.getSeasons().contains(instance.getCalendar().getSeason())) {
                    if (d >= mob.getProbabilityFrom() && d <= mob.getProbabilityTo()) {
                        mob.getClazz().getConstructor(Location.class).newInstance(event.getLocation());
                        damageable.remove();
                        return;
                    }
                }
            }
        }
    }

}
