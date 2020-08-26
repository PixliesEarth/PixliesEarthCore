package eu.pixliesearth.core.machines.autocrafters.tinkertable;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class TinkerTableListener implements Listener {

    private static final Main instance = Main.getInstance();

    public TinkerTableListener() {
        //TODO REMOVE
        Bukkit.addRecipe(new ShapedRecipe(NamespacedKey.minecraft(NamespacedKey.BUKKIT), TinkerTable.item).shape("sss", "lcl", "lpl").setIngredient('s', new ItemStack(Material.SPRUCE_SLAB)).setIngredient('l', new ItemStack(Material.OAK_LOG)).setIngredient('c', new ItemStack(Material.CRAFTING_TABLE)).setIngredient('p', new ItemStack(Material.SPRUCE_PLANKS)));
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block b = event.getClickedBlock();
        if (b == null) return;
        if (!instance.getUtilLists().machines.containsKey(b.getLocation())) return;
        instance.getUtilLists().openMachines.put(event.getPlayer().getUniqueId(),  instance.getUtilLists().machines.get(b.getLocation()));
        instance.getUtilLists().machines.get(b.getLocation()).open(event.getPlayer());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (!item.isSimilar(TinkerTable.item)) return;
        instance.getUtilLists().machines.put(event.getBlockPlaced().getLocation(), new TinkerTable(Methods.generateId(7), event.getBlockPlaced().getLocation()));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!instance.getUtilLists().machines.containsKey(event.getBlock().getLocation())) return;
        event.setDropItems(false);
        Machine mill = instance.getUtilLists().machines.get(event.getBlock().getLocation());
        mill.getLocation().getWorld().dropItemNaturally(mill.getLocation(), mill.getItem());
        mill.remove();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().startsWith("§b§lTinker Table §8| ")) return;
        if (event.getClickedInventory() == null) return;
        if (TinkerTable.craftSlots.contains(event.getSlot()) || TinkerTable.resultSlots.contains(event.getSlot())) return;
        if (!event.getClickedInventory().equals(view.getTopInventory())) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.BARRIER && event.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lBack")) {
            TinkerTable targetMill = (TinkerTable) instance.getUtilLists().openMachines.get(event.getWhoClicked().getUniqueId());
            for (int i : TinkerTable.craftSlots) {
                if (event.getClickedInventory().getItem(i) != null)
                    event.getWhoClicked().getWorld().dropItemNaturally(targetMill.getLocation(), event.getClickedInventory().getItem(i));
            }
            for (int i : TinkerTable.resultSlots) {
                if (event.getClickedInventory().getItem(i) != null)
                    event.getWhoClicked().getWorld().dropItemNaturally(targetMill.getLocation(), event.getClickedInventory().getItem(i));
            }
            targetMill.reopen((Player) event.getWhoClicked());
        }
    }

}