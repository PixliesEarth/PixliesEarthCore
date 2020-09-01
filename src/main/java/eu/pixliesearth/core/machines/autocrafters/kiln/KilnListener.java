package eu.pixliesearth.core.machines.autocrafters.kiln;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.core.machines.autocrafters.forge.bronze.BronzeForge;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
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

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().startsWith("§c§lKiln §8| ")) return;
        if (event.getClickedInventory() == null) return;
        if (AutoCrafterMachine.craftSlots.contains(event.getSlot()) || AutoCrafterMachine.resultSlots.contains(event.getSlot())) return;
        if (!event.getClickedInventory().equals(view.getTopInventory())) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.BARRIER && event.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lBack")) {
            Kiln targetMill = (Kiln) instance.getUtilLists().openMachines.get(event.getWhoClicked().getUniqueId());
            for (int i : Kiln.craftSlots) {
                if (event.getClickedInventory().getItem(i) != null)
                    event.getWhoClicked().getWorld().dropItemNaturally(targetMill.getLocation(), event.getClickedInventory().getItem(i));
            }
            for (int i : Kiln.resultSlots) {
                if (event.getClickedInventory().getItem(i) != null)
                    event.getWhoClicked().getWorld().dropItemNaturally(targetMill.getLocation(), event.getClickedInventory().getItem(i));
            }
            targetMill.reopen((Player) event.getWhoClicked());
        }
    }

}
