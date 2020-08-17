package eu.pixliesearth.core.machines.kiln;

import eu.pixliesearth.Main;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class KilnListener implements Listener {

    private static final Main instance = Main.getInstance();

    public KilnListener() {
        Bukkit.addRecipe(new ShapedRecipe(NamespacedKey.minecraft(NamespacedKey.randomKey().getKey()), Kiln.item).shape("sss", "lcl", "lpl").setIngredient('s', new ItemStack(Material.SPRUCE_SLAB)).setIngredient('l', new ItemStack(Material.OAK_LOG)).setIngredient('c', new ItemStack(Material.LOOM)).setIngredient('p', new ItemStack(Material.SPRUCE_PLANKS)));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (!item.isSimilar(Kiln.item)) return;
        instance.getUtilLists().machines.put(event.getBlockPlaced().getLocation(), new Kiln(Methods.generateId(7), event.getBlockPlaced().getLocation()));
    }

}
