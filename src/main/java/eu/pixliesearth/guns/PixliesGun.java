package eu.pixliesearth.guns;

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
    private int maxAmmo;
    private int accuracy;
    private long delay;
    private List<Action> triggers;

    public void trigger(PlayerInteractEvent event) {
        if (!triggers.contains(event.getAction())) return;
        Player player = event.getPlayer();
        PixliesAmmo ammo = ammoType.getAmmo().createNewOne(player.getLocation(), this);
        boolean useAmmo = Methods.removeRequiredAmount(ammo.getItem(), player.getInventory());
        if (!useAmmo) {
            player.sendActionBar("§c§lNEED TO RELOAD!");
            player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
            return;
        }
        PixliesGunShootEvent shootEvent = new PixliesGunShootEvent(player, ammo);
        Bukkit.getPluginManager().callEvent(shootEvent);
        if (!shootEvent.isCancelled()) {
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

    }

}
