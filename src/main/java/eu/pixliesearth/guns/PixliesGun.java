package eu.pixliesearth.guns;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.guns.guns.*;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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

    protected static final Map<String, Class<? extends PixliesGun>> guns = new HashMap<>();
    protected static Main instance = Main.getInstance();

    protected UUID uuid;
    protected String name;
    protected ItemStack item;
    protected AmmoType ammoType;
    protected int maxRange;
    protected int ammo;
    protected int maxAmmo;
    protected double accuracy;
    protected long delay;
    protected List<Action> triggers;

    public void trigger(final PlayerInteractEvent event) {
        ItemStack eventItem = event.getItem();
        if (eventItem == null) return;
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
            reloadItem(eventItem);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
            GunFireResult result = ammo.trace(player);
            if (result == null) return;
            if (result.getEntity() instanceof Player && ((Player) result.getEntity()).getGameMode() != GameMode.SURVIVAL && ((Player) result.getEntity()).getGameMode() != GameMode.ADVENTURE) return;
            if (result.isHeadshot()) {
                result.getEntity().setLastDamageCause(new EntityDamageByEntityEvent(player, result.getEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, ammo.getDamage()));
                result.getEntity().setHealth(result.getEntity().getHealth() - (ammo.getDamage() * 2));
                result.getEntity().getWorld().playSound(result.getEntity().getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
            } else {
                result.getEntity().setLastDamageCause(new EntityDamageByEntityEvent(player, result.getEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, ammo.getDamage()));
                result.getEntity().setHealth(result.getEntity().getHealth() - ammo.getDamage());
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

    public static void loadGuns() {
        guns.put(new AK47(0, UUID.randomUUID()).getName(), AK47.class);
        guns.put(new K98K(0, UUID.randomUUID()).getName(), K98K.class);
        guns.put(new M16(0, UUID.randomUUID()).getName(), M16.class);
        guns.put(new MP5(0, UUID.randomUUID()).getName(), MP5.class);
        guns.put(new RPG7(0, UUID.randomUUID()).getName(), RPG7.class);
        guns.put(new Slingshot(0, UUID.randomUUID()).getName(), Slingshot.class);
        guns.put(new Uzi(0, UUID.randomUUID()).getName(), Uzi.class);
    }

    public static PixliesGun getByItem(ItemStack item) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (guns.isEmpty()) loadGuns();
        if (!item.hasItemMeta()) return null;
        if (!guns.containsKey(item.getItemMeta().getDisplayName().split(" §8| ")[0])) return null;
        int ammo = Integer.parseInt(StringUtils.substringBetween(item.getItemMeta().getDisplayName(), "[§c", "§8]").split("§7/")[0].replace("§c", ""));
        NBTUtil.NBTTags tags = NBTUtil.getTagsFromItem(item);
        if (tags == null) return null;
        String uuidS = tags.getString("gunId");
        if (uuidS == null) return null;
        UUID uuid = UUID.fromString(uuidS);
        if (item.getLore() == null) return null;
        return guns.get(item.getItemMeta().getDisplayName().split(" §8| ")[0]).getConstructor(int.class, UUID.class).newInstance(ammo, uuid);
    }

}
