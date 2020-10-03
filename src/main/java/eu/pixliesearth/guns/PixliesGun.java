package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.guns.guns.*;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static eu.pixliesearth.guns.PixliesAmmo.AmmoType;

@Data
@AllArgsConstructor
public class PixliesGun {

    private static Main instance = Main.getInstance();

    private UUID uuid;
    private String name;
    private ItemStack item;
    private AmmoType ammoType;
    private int maxRange;
    private int ammo;
    private int maxAmmo;
    private double accuracy;
    private long delay;
    private List<Action> triggers;

    public void trigger(final PlayerInteractEvent event) {
        if (!triggers.contains(event.getAction())) return;
        if (instance.getUtilLists().waitingGuns.containsKey(uuid)) return;
        instance.getUtilLists().waitingGuns.put(uuid, new Timer(delay));
        Player player = event.getPlayer();
        PixliesAmmo ammo = ammoType.getAmmo().createNewOne(player.getEyeLocation(), this);
        if (this.ammo <= 0) {
            reload(event);
            return;
        }
        if (player.isSwimming()) {
            player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            return;
        }
        PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(player, ammo);
        Bukkit.getPluginManager().callEvent(shootEvent);
        if (!shootEvent.isCancelled()) {
            this.ammo -= 1;
            reloadItem(event.getItem());
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
            float newPitch = player.getLocation().getPitch() - 4;
            Location newLocation = player.getLocation();
            newLocation.setPitch(newPitch);
            player.teleport(newLocation);
            GunFireResult result = ammo.trace(player);
            if (result == null) return;
            if (result.getEntity() instanceof Player && ((Player) result.getEntity()).getGameMode() != GameMode.SURVIVAL && ((Player) result.getEntity()).getGameMode() != GameMode.ADVENTURE) return;
            if (result.isHeadshot()) {
                result.getEntity().setKiller(player);
                result.getEntity().setHealth(0.0);
            } else {
                result.getEntity().damage(ammo.getDamage(), player);
            }
            result.getPositionLocation().getWorld().spawnParticle(Particle.REDSTONE, result.getPositionLocation(), 5, new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 0, 0), 15));
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
        player.sendActionBar("§b§lReloading...");
        Bukkit.getScheduler().runTaskLaterAsynchronously(instance, () -> {
            player.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
            ammo = maxAmmo;
            player.sendActionBar("§a§lReloaded!");
            if (event.getItem() == null) return;
            reloadItem(event.getItem());
        }, 20 * 2);
    }

    public void reloadItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name + " §8| §8[§c" + getAmmo() + "§7/§c" + getMaxAmmo() + "§8]");
        item.setItemMeta(meta);
    }

    public static Map<String, Class<? extends PixliesGun>> classMap() {
        Map<String, Class<? extends PixliesGun>> map = new HashMap<>();
        map.put("§c§lM-16", M16.class);
        map.put("§c§lAK-47", AK47.class);
        map.put("§3§lUzi", Uzi.class);
        map.put("§c§lMP5", MP5.class);
        map.put("§c§lKarabiner98k", K98K.class);
        map.put("§6Slingshot", Slingshot.class);
        return map;
    }

    public static PixliesGun getByItem(ItemStack item) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Map.Entry<String, Class<? extends PixliesGun>> entry : classMap().entrySet())
            if (item.hasItemMeta() && item.getItemMeta().getDisplayName().split(" §8| ")[0].equals(entry.getKey())) {
                int ammo = Integer.parseInt(StringUtils.substringBetween(item.getItemMeta().getDisplayName(), "[§c", "§8]").split("§7/")[0].replace("§c", ""));
                NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
                if (tags == null) return null;
                String uuidS = tags.getString("gunId");
                if (uuidS == null) return null;
                UUID uuid = UUID.fromString(uuidS);
                if (item.getLore() == null) return null;
                return entry.getValue().getConstructor(int.class, UUID.class).newInstance(ammo, uuid);
            }
        return null;
    }

}
