package eu.pixliesearth.guns.listeners;

import eu.pixliesearth.Main;
import eu.pixliesearth.guns.Gun;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Objects;

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
}
