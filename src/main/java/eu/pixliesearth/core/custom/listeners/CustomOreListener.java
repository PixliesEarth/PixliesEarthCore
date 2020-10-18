package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.BlockDrop;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.listener.ProtectionManager;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Random;

public class CustomOreListener extends CustomListener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Block b = event.getBlock();
        if (!CustomFeatureLoader.getLoader().getHandler().getDropMap().containsKey(b.getType())) return;
        if (event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (event.getPlayer().getInventory().getItemInOffHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!ProtectionManager.canBreak(event)) return;
        if (!getChance(15)) return;
        BlockDrop drop = getRandomItem(CustomFeatureLoader.getLoader().getHandler().getDropMap().get(b.getType()));
        b.getWorld().dropItemNaturally(b.getLocation(), drop.getToDrop());
    }

    private static boolean getChance(int percentage) {
        return (new Random().nextInt(100) <= percentage);
    }

    private static BlockDrop getRandomItem(List<BlockDrop> list) {
        return list.get(new Random().nextInt(list.size()));
    }

}
