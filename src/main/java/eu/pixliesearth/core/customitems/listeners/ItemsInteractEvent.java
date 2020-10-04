package eu.pixliesearth.core.customitems.listeners;

import eu.pixliesearth.core.customitems.CustomItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemsInteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        CustomItems ci = CustomItems.getByItemStack(item);
        if (ci == null) return;
        ci.clazz.onInteract(event);
    }

    public static void removeOne(ItemStack i, Player p) {
        HashMap<Integer, ? extends ItemStack> map = p.getInventory().all(i);
        if (map.isEmpty()) {
            return;
        }
        for (Map.Entry<Integer, ? extends ItemStack> entry : map.entrySet())
            if (entry.getValue().getAmount() != 0) {
                if (entry.getValue().getAmount() == 1) p.getPlayer().getInventory().setItem(entry.getKey(), null);
                else {
                    entry.getValue().setAmount(entry.getValue().getAmount() - 1);
                    p.getPlayer().getInventory().setItem(entry.getKey(), entry.getValue());
                }
                break;
            }
    }


}