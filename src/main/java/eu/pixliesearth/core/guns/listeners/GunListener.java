package eu.pixliesearth.core.guns.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.guns.Gun;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

public class GunListener implements Listener {
    private final Main plugin;

    public GunListener(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (e.getHand() == null || e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        Gun gun = Gun.getByItem(p.getInventory().getItemInMainHand());
        if (gun == null) return;
        if (gun.automatic) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                gun.shoot(p);
            }
        } else {
            if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                gun.shoot(p);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof LivingEntity) {
            if (plugin.getUtilLists().ammos.containsKey(event.getDamager())) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.damage(plugin.getUtilLists().ammos.get(event.getDamager()));
                Player player = (Player) ((Snowball) event.getDamager()).getShooter();
                entity.setVelocity(player.getLocation().getDirection().setY(0).normalize());
                Main.getInstance().getUtilLists().ammos.remove(event.getDamager());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.NEUTRAL, 2, 1);
            }
        }
    }

}
