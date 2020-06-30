package eu.pixliesearth.nations.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MapClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getType().equals(Material.AIR)) return;
        if (item.getItemMeta().getDisplayName().equals("")) return;
        if (!event.getView().getTitle().equals("§bClaim-map")) return;
        if (item.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();

        int x = Integer.parseInt(item.getItemMeta().getDisplayName().split("§8, ")[0].replace("§b", ""));
        int z = Integer.parseInt(item.getItemMeta().getDisplayName().split("§8, ")[1].replace("§b", ""));

        player.performCommand("n claim " + x + ";" + z);
        player.closeInventory();
        player.performCommand("n map gui");
    }

}
