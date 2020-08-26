package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.guns.guns.M16;
import eu.pixliesearth.utils.Methods;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //TODO: shooting delay
    public void trigger(final PlayerInteractEvent event) {
        if (!triggers.contains(event.getAction())) return;
        Player player = event.getPlayer();
        PixliesAmmo ammo = ammoType.getAmmo().createNewOne(player.getEyeLocation(), this);
        if (this.ammo <= 0) {
            reload(event);
            return;
        }
        PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(player, ammo);
        Bukkit.getPluginManager().callEvent(shootEvent);
        if (!shootEvent.isCancelled()) {
            this.ammo -= 1;
            Methods.removeRequiredAmount(event.getItem(), player.getInventory());
            player.getInventory().setItemInMainHand(reloadItem());
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
            player.getLocation().setYaw(player.getLocation().getYaw() + 0.8f);
            GunFireResult result = ammo.trace(player);
            if (result == null) return;
            if (result.isHeadshot()) {
                result.getEntity().setKiller(player);
                result.getEntity().setHealth(0.0);
            } else {
                result.getEntity().damage(ammo.getDamage(), player);
            }
            result.getPositionLocation().getWorld().spawnParticle(Particle.REDSTONE, result.getPositionLocation(), 5, new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 0, 0), 1));
        }
    }

    public void reload(final PlayerInteractEvent event) {
        ItemStack needed = ammoType.getAmmo().getItem();
        Player player = event.getPlayer();
        boolean hasItem = Methods.removeRequiredAmount(needed, player.getInventory());
        if (!hasItem) {
            player.sendActionBar("§c§lNO AMMO!");
            player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            return;
        }
        Methods.removeRequiredAmount(event.getItem(), player.getInventory());
        player.sendActionBar("§7§lReloading...");
        player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            ammo = maxAmmo;
            player.sendActionBar("§a§lReloaded!");
            player.getInventory().setItemInMainHand(reloadItem());
        }, 40);
    }

    public ItemStack reloadItem() {
        return new ItemStack(Material.STICK);
    }

    public static Map<String, Class<? extends PixliesGun>> classMap() {
        Map<String, Class<? extends PixliesGun>> map = new HashMap<>();
        map.put("§c§lM-16", M16.class);
        return map;
    }

    public static PixliesGun getByItem(ItemStack item) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Map.Entry<String, Class<? extends PixliesGun>> entry : classMap().entrySet())
            if (item.hasItemMeta() && item.getItemMeta().getDisplayName().split(" §8| ")[0].equals(entry.getKey())) {
                int ammo = Integer.parseInt(StringUtils.substringBetween(item.getItemMeta().getDisplayName(), "[§c", "§8]").split("§7/")[0].replace("§c", ""));
                return entry.getValue().getConstructor(int.class).newInstance(ammo);
            }
        return null;
    }

}
