package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomMob;
import eu.pixliesearth.utils.Methods;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
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
            event.getEntity().setCustomName(event.getEntity().getName().replace("§8[§c||||||||||§8]", "") + " §8[" + Methods.getProgressBar(damageable.getHealth(), damageable.getMaxHealth(), 10, "|", "&c", "&7") + "§8]");
            event.getEntity().setCustomNameVisible(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
/*        ArmorStand a = entity.getLocation().getWorld().spawn(entity.getLocation(), ArmorStand.class);
        a.setVisible(false);
        a.setCustomName("§c-" + event.getDamage());
        a.setCustomNameVisible(true);
        a.setGravity(false);
        a.setBasePlate(false);
        a.setSmall(true);
        Bukkit.getScheduler().runTaskLaterAsynchronously(instance, a::remove, 20);*/
        if (entity instanceof Player) return;
        if (!(entity instanceof Damageable)) return;
        Damageable damageable = (Damageable) entity;
        String health = StringUtils.substringBetween(entity.getCustomName(), "§8[", "§8]");
        if (health == null) return;
        entity.setCustomName(entity.getCustomName().replace(health, Methods.getProgressBar(damageable.getHealth(), damageable.getMaxHealth(), 10, "|", "&c", "&7")));
    }

}
