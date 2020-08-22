package eu.pixliesearth.core.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.listeners.ItemsInteractEvent;
import eu.pixliesearth.events.ShootEvent;
import lombok.AllArgsConstructor;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public class Gun {

    public double damage;

    public int maxAmmo;

    public boolean automatic;

    public Ammo ammoType;

    public ItemStack getItem(int ammo) { return null; };

    public static int ammoLeft(ItemStack item) {
        if (item.getLore() == null) return 0;
        for (String s : item.getLore()) {
            if (s.startsWith("§7Ammo: §f")) {
                return Integer.parseInt(StringUtils.substringBetween(s, "§7Ammo: §f", "/"));
            }
        }
        return 0;
    }

    public void shoot(Player player) {
        if (ammoLeft(player.getInventory().getItemInMainHand()) == 0) {
            if (player.getInventory().contains(ammoType.getItem(), 1)) {
                ItemsInteractEvent.removeOne(ammoType.getItem(), player);
                player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                player.sendActionBar("§aReloaded.");
                player.getInventory().setItemInMainHand(getItem(maxAmmo));
                return;
            }
            player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            player.sendActionBar("§cYou need to reload your gun!");
            return;
        }
        final int ammoLeft = ammoLeft(player.getInventory().getItemInMainHand());
        System.out.println(ammoLeft - 1);
        player.getInventory().setItemInMainHand(getItem(ammoLeft - 1));
        AtomicReference<Snowball> sb = new AtomicReference<>();
        Bukkit.getPluginManager().callEvent(new ShootEvent(player, ammoType.getName()));
        player.getWorld().spawn(player.getEyeLocation(), Snowball.class, snowball -> {
            snowball.setShooter(player);
            snowball.setVelocity(player.getEyeLocation().getDirection().multiply(2.0));
            snowball.setSilent(true);
            snowball.setCustomName("§cGun Bullet");
            snowball.setBounce(false);
            snowball.setGravity(false);
            Main.getInstance().getUtilLists().ammos.put(snowball, damage * 2D);
            sb.set(snowball);
        });
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.NEUTRAL, 10, 10);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            sb.get().remove();
            Main.getInstance().getUtilLists().ammos.remove(sb.get());
        }, 20 * 5);
    }

    public static Gun getByItem(@Nonnull ItemStack item) {
        for (Guns guns : Guns.values()) {
            if (guns.getClazz().getItem(ammoLeft(item)).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()) && guns.getClazz().getItem(ammoLeft(item)).getType().equals(item.getType()))
                return guns.getClazz();
        }
        return null;
    }

}
