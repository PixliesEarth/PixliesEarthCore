package eu.pixliesearth.core.machines.ingotforge;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.carpentrymill.CarpentryMill;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class IngotForgeListener implements Listener {

    private static final Main instance = Main.getInstance();

    public IngotForgeListener() {
        Bukkit.addRecipe(new ShapedRecipe(NamespacedKey.minecraft(NamespacedKey.randomKey().getKey()), IngotForge.item).shape("sss", "lcl", "lpl").setIngredient('s', new ItemStack(Material.SPRUCE_SLAB)).setIngredient('l', new ItemStack(Material.OAK_LOG)).setIngredient('c', new ItemStack(Material.LOOM)).setIngredient('p', new ItemStack(Material.SPRUCE_PLANKS)));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (!item.isSimilar(IngotForge.item)) return;
        instance.getUtilLists().machines.put(event.getBlockPlaced().getLocation(), new IngotForge(Methods.generateId(7), event.getBlockPlaced().getLocation()));
    }

}
