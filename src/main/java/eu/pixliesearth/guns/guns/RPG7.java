package eu.pixliesearth.guns.guns;

import eu.pixliesearth.guns.GunFireResult;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.guns.RPGFireResult;
import eu.pixliesearth.guns.events.PixliesGunShootEvent;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class RPG7 extends PixliesGun {

    public RPG7(int ammo, UUID uuid) {
        super(uuid, "§6RPG-7", new ItemBuilder(Material.GOLDEN_HOE).setCustomModelData(69).setDisplayName("§6RPG-7 §8| §8[§c1§7/§c1§8]").addLoreLine("§7Ammo: §3Rocket").addLoreLine("§7Origin: §bUSA").addLoreLine("§7Range: §350 blocks").addLoreLine("§7Accuracy: §30.1").addNBTTag("gunId", uuid.toString(), NBTTagType.STRING).build(), PixliesAmmo.AmmoType.ROCKET, 50, ammo,1, 0.1, 1500, Arrays.asList(RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK));
    }

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
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2, 2);
            RPGFireResult result = ammo.traceRPG(player);
            if (result == null) return;
            Bukkit.getScheduler().runTaskLater(instance, () -> result.getLocation().createExplosion(2F, true), 40L);
        }
    }


}
