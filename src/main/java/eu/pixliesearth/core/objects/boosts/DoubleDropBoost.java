package eu.pixliesearth.core.objects.boosts;

import eu.pixliesearth.core.objects.Boost;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DoubleDropBoost extends Boost implements Listener {

    public DoubleDropBoost() {
        super("2x ore-drops", BoostType.DOUBLE_DROP, new Timer(600000));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().name().contains("ORE")) return;
        if (event.isDropItems()) event.setDropItems(false);
        switch (event.getBlock().getType()) {
            case COAL_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COAL, 2));
                break;
            case IRON_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_ORE, 2));
                break;
            case GOLD_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_ORE, 2));
                break;
            case DIAMOND_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DIAMOND, 2));
                break;
            case EMERALD_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.EMERALD, 2));
                break;
            case REDSTONE_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.REDSTONE, 2));
                break;
            case NETHER_QUARTZ_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.QUARTZ, 2));
                break;
            case LAPIS_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.LAPIS_LAZULI, 2));
                break;
            case NETHER_GOLD_ORE:
                event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_NUGGET, 2));
                break;
        }
    }

}
