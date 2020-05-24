package eu.pixliesearth.guns.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.Gun;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AkGun implements Listener {
    private Main plugin;

    public AkGun(Main plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            if(p.getInventory().getItemInMainHand() == null) return;
            if(p.getInventory().getItemInMainHand().getItemMeta() == null) return;
            if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cAK47")){
                Gun.shoot(p);
            }
        }
    }
}
