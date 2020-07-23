package eu.pixliesearth.core.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.events.ShootEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

public class Gun {

    public double damage() { return 3D; };

    public int maxAmmo() { return 30; };

    public boolean automatic() { return true; };

    public ItemStack getItem(int ammo) { return null; };

    public void shoot(Player player) {
        AtomicReference<Snowball> sb = new AtomicReference<>();
        Bukkit.getPluginManager().callEvent(new ShootEvent(player, "§cGun Bullet"));
        player.getWorld().spawn(player.getEyeLocation(), Snowball.class, snowball -> {
            snowball.setShooter(player);
            snowball.setVelocity(player.getEyeLocation().getDirection().multiply(2.0));
            snowball.setSilent(true);
            snowball.setCustomName("§cGun Bullet");
            snowball.setBounce(false);
            snowball.setGravity(false);
            Main.getInstance().getUtilLists().ammos.put(snowball, damage() * 2D);
            sb.set(snowball);
        });
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.NEUTRAL, 10, 10);
/*        new BukkitRunnable() {
            public void run() {
                if (!Main.getInstance().getUtilLists().ammos.containsKey(sb.get())) {
                    this.cancel();
                    return;
                }
                sb.get().getWorld().spawnParticle(Particle.SMOKE_NORMAL, sb.get().getLocation(), 1);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);*/
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            sb.get().remove();
            Main.getInstance().getUtilLists().ammos.remove(sb.get());
        }, 20 * 30);
    }

    public static Gun getByItem(@Nonnull ItemStack item) {
        for (Guns guns : Guns.values()) {
            if (guns.getClazz().getItem(guns.getClazz().maxAmmo()).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()) && guns.getClazz().getItem(guns.getClazz().maxAmmo()).getType().equals(item.getType()))
                return guns.getClazz();
        }
        return null;
    }

}
