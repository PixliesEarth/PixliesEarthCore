package eu.pixliesearth.core.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicReference;

public class Gun {

    public static void shoot(Player player){
        AtomicReference<Snowball> sb = new AtomicReference<>();
        Bukkit.getPluginManager().callEvent(new ShootEvent(player, "§c7.62"));
        player.getWorld().spawn(player.getEyeLocation(), Snowball.class, snowball -> {
            snowball.setShooter(player);
            snowball.setVelocity(player.getEyeLocation().getDirection().multiply(2.0));
            snowball.setSilent(true);
            snowball.setCustomName("§c7.62mm");
            snowball.setBounce(false);
            snowball.setGravity(false);
            Main.getInstance().getPlayerLists().ammos.put(snowball, 6D);
            sb.set(snowball);
        });
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.NEUTRAL, 10, 1);
        new BukkitRunnable() {
            public void run() {
                if (!Main.getInstance().getPlayerLists().ammos.containsKey(sb.get())) {
                    this.cancel();
                    return;
                }
                sb.get().getWorld().spawnParticle(Particle.SMOKE_NORMAL, sb.get().getLocation(), 1);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            sb.get().remove();
            if (Main.getInstance().getPlayerLists().ammos.containsKey(sb.get()))
                Main.getInstance().getPlayerLists().ammos.remove(sb.get());
        }, 20 * 60);
    }
}
