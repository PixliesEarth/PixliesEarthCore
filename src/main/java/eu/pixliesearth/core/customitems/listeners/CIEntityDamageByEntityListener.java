package eu.pixliesearth.core.customitems.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class CIEntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        ItemStack tool = damager.getInventory().getItemInMainHand();
        if (tool == null || tool.getType() == Material.AIR) return;
        if (tool.getLore() == null) return;
        for (String s : tool.getLore())
            if (s.startsWith("§2Damage: ")) {
                double damage = Double.parseDouble(s.split(": ")[1]);
                event.setDamage(damage);
                break;
            }

    }

}
