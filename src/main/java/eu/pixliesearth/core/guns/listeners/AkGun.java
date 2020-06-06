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

import java.util.ArrayList;

public class AkGun implements Listener {
    private Main plugin;

    public AkGun(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(p.getInventory().getItemInMainHand() == null) return;
            if(p.getInventory().getItemInMainHand().getItemMeta() == null) return;
            if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cAK47")){
                if(p.getInventory().getItemInMainHand().getItemMeta().getLore() == null) return;
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("§7Ammo: §f30/30");
                lore.add("§7Damage: §f3.0");
                lore.add("§7Type: §f7.62mm");
                if(p.getInventory().getItemInMainHand().getItemMeta().getLore().equals(lore))
                Gun.shoot(p);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof LivingEntity) {
            if (plugin.getPlayerLists().ammos.containsKey(event.getDamager())) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                entity.damage(plugin.getPlayerLists().ammos.get(event.getDamager()));
                Player player = (Player) ((Snowball) event.getDamager()).getShooter();
                entity.setVelocity(player.getLocation().getDirection().setY(0).normalize());
                Main.getInstance().getPlayerLists().ammos.remove(event.getDamager());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.NEUTRAL, 2, 1);
            }
        }
    }

}
