package eu.pixliesearth.core.listener;

import eu.pixliesearth.core.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.IRON_ORE) || event.getBlock().getType().equals(Material.DIAMOND_ORE) || event.getBlock().getType().equals(Material.GOLD_ORE)) {
            Random r = new Random();
            if (r.nextDouble() < 0.25) {
                event.getBlock().getDrops().add(new ItemBuilder(Material.POTION).setDisplayName("§e§lENERGY BOTTLE §8(§f§l2§8)").addLoreLine("§7§oRight-click to use").build());
            }
        }
    }

}
