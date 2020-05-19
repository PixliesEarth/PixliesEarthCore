package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.objects.Energy;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getItem() == null) return;
        if (event.getMaterial() == Material.AIR) return;
        if (event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().equals("§e§lENERGY BOTTLE §8(§f§l2§8)")) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                event.getPlayer().getInventory().remove(event.getItem());
                Energy.add(Main.getInstance().getProfile(event.getPlayer().getUniqueId()), 2.0);
            }
        }
    }

}
