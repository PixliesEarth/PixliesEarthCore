package eu.pixliesearth.core.guns.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.guns.Gun;
import eu.pixliesearth.guns.PixliesGun;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GunListener implements Listener {
    private final Main plugin;

    public GunListener(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getHand() == null || event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        PixliesGun gun = PixliesGun.getByItem(player.getInventory().getItemInMainHand());
        if (gun == null) return;
        gun.trigger(event);
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
