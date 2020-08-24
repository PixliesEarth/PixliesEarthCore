package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.ammo.RifleAmmo;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.utils.Methods;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static eu.pixliesearth.guns.PixliesAmmo.AmmoType;

@Data
@AllArgsConstructor
public class PixliesGun {

    private String name;
    private ItemStack item;
    private AmmoType ammoType;
    private int maxRange;
    private int ammo;
    private int maxAmmo;
    private double accuracy;
    private long delay;
    private List<Action> triggers;

    public void trigger(PlayerInteractEvent event) {
        if (!triggers.contains(event.getAction())) return;
        Player player = event.getPlayer();
        PixliesAmmo ammo = ammoType.getAmmo().createNewOne(player.getLocation(), this);
        if (this.ammo <= 0) {
            reload(event);
            return;
        }
        PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(player, ammo);
        Bukkit.getPluginManager().callEvent(shootEvent);
        if (!shootEvent.isCancelled()) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
            player.getLocation().setPitch(player.getLocation().getPitch() + 0.4f);
            GunFireResult result = ammo.trace(player);
            if (result == null) return;
            if (result.isHeadshot()) {
                result.getEntity().setKiller(player);
                result.getEntity().setHealth(0.0);
            } else {
                result.getEntity().damage(ammo.getDamage(), player);
            }
        }
    }

    public void reload(PlayerInteractEvent event) {
        ItemStack needed = ammoType.getAmmo().getItem();
        Player player = event.getPlayer();
        boolean hasItem = Methods.removeRequiredAmount(needed, player.getInventory());
        if (!hasItem) {
            player.sendActionBar("§c§lNO AMMO!");
            player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            return;
        }
        player.sendActionBar("§7§lReloading...");
        player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            ammo = maxAmmo;
            player.sendActionBar("§a§lReloaded!");
        }, 40);
    }

}
