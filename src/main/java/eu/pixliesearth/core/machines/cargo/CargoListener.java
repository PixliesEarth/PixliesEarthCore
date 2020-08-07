package eu.pixliesearth.core.machines.cargo;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.carpentrymill.CarpentryMill;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CargoListener implements Listener {

    private static final Main instance = Main.getInstance();

    public CargoListener() {
        Bukkit.addRecipe(new ShapedRecipe(NamespacedKey.randomKey(), InputNode.item).shape("   ", "pcp", "   ").setIngredient('p', new ItemStack(Material.OAK_PLANKS)).setIngredient('c', new ItemStack(Material.CRAFTING_TABLE)));
        Bukkit.addRecipe(new ShapedRecipe(NamespacedKey.randomKey(), OutputNode.item).shape("   ", "scs", "   ").setIngredient('s', new ItemStack(Material.STICK)).setIngredient('c', new ItemStack(Material.CRAFTING_TABLE)));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.isSimilar(InputNode.item)) {
            instance.getUtilLists().machines.put(event.getBlockPlaced().getLocation(), new InputNode(Methods.generateId(7), event.getBlockPlaced().getLocation()));
        } else if (item.isSimilar(OutputNode.item)) {
            instance.getUtilLists().machines.put(event.getBlockPlaced().getLocation(), new OutputNode(Methods.generateId(7), event.getBlockPlaced().getLocation()));
        }
    }

}
