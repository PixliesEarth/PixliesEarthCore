package eu.pixliesearth.core.listener;

import eu.pixliesearth.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (Main.getInstance().getUtilLists().deathChests.containsKey(event.getClickedBlock())) {
            event.setCancelled(true);
            for (ItemStack item : Main.getInstance().getUtilLists().deathChests.get(event.getClickedBlock()))
                if (item != null)
                    event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), item);
            Main.getInstance().getUtilLists().deathChests.remove(event.getClickedBlock());
            event.getClickedBlock().setType(Material.AIR);
        }
    }

}
