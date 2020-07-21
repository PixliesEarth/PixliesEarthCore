package eu.pixliesearth.core.customcrafting;

import eu.pixliesearth.core.customitems.CustomItems;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomCrafting implements Listener {

    static final List<Integer> craftingSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    static final List<Integer> bottomBarSlots = Arrays.asList(45, 46, 47, 48, 50, 51, 52, 53);

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "§e§lCrafting");
        for (int i = 0; i < inv.getSize(); i++)
            if (!craftingSlots.contains(i) && !bottomBarSlots.contains(i) && i != 24)
                inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
        for (int i : bottomBarSlots)
            inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lWRONG RECIPE").build());
        inv.setItem(49, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§b§lRECIPES").build());
        player.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        Inventory inventory = event.getClickedInventory();
        if (inventory != null && inventory.equals(view.getTopInventory())) {
            ItemStack item = event.getCurrentItem();
            if (item != null && (item.getType().equals(Material.RED_STAINED_GLASS_PANE) || item.getType().equals(Material.LIME_STAINED_GLASS_PANE) || item.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) && item.getItemMeta().getDisplayName().equals(" "))
                event.setCancelled(true);
            if (event.getSlot() == 49)
                event.setCancelled(true);
            if (event.getSlot() == 24) {
                for (int i : craftingSlots)
                    inventory.clear(i);
                for (int i : bottomBarSlots)
                    inventory.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lWRONG RECIPE").build());
                event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
            ItemStack result = getResult(inventory);
            if (result != null) {
                for (int i : bottomBarSlots)
                    inventory.setItem(i, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§a§lRIGHT RECIPE").build());
                inventory.setItem(24, result);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        for (int i : craftingSlots)
            if (event.getInventory().getItem(i) != null)
                event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(i));
        if (event.getInventory().getItem(24) != null)
            event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getInventory().getItem(24));
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
    }

    private ItemStack getResult(final Inventory inventory) {
        for (CustomItems customItems : CustomItems.values()) {
            if (customItems.recipe == null) continue;
            CustomRecipe recipe = customItems.recipe;
            if (!Objects.equals(inventory.getItem(10), recipe.getS1())) continue;
            if (!Objects.equals(inventory.getItem(11), recipe.getS2())) continue;
            if (!Objects.equals(inventory.getItem(12), recipe.getS3())) continue;
            if (!Objects.equals(inventory.getItem(19), recipe.getS4())) continue;
            if (!Objects.equals(inventory.getItem(20), recipe.getS5())) continue;
            if (!Objects.equals(inventory.getItem(21), recipe.getS6())) continue;
            if (!Objects.equals(inventory.getItem(28), recipe.getS7())) continue;
            if (!Objects.equals(inventory.getItem(29), recipe.getS8())) continue;
            if (!Objects.equals(inventory.getItem(30), recipe.getS9())) continue;
            return recipe.getResult();
        }
        return null;
    }

}
