package eu.pixliesearth.core.craftingsystem;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import eu.pixliesearth.Main;
import eu.pixliesearth.utils.ItemBuilder;
import net.minecraft.server.v1_16_R1.*;
import net.minecraft.server.v1_16_R1.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CraftingManager implements Listener {

    static final List<Integer> craftingSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    static final List<Integer> bottomBarSlots = Arrays.asList(45, 46, 47, 48, 50, 51, 52, 53);

    static private ContainerWorkbench containerWorkbench;
    static private InventoryCrafting ic;

    private static ItemStack craft(List<ItemStack> ingredients) {

        containerWorkbench = new ContainerWorkbench(-1, new PlayerInventory(null));
        ic = new InventoryCrafting(containerWorkbench, 3, 3, null);
        ic.setItem(0, CraftItemStack.asNMSCopy(ingredients.get(0)));
        ic.setItem(1, CraftItemStack.asNMSCopy(ingredients.get(1)));
        ic.setItem(2, CraftItemStack.asNMSCopy(ingredients.get(2)));
        ic.setItem(3, CraftItemStack.asNMSCopy(ingredients.get(3)));
        ic.setItem(4, CraftItemStack.asNMSCopy(ingredients.get(4)));
        ic.setItem(5, CraftItemStack.asNMSCopy(ingredients.get(5)));
        ic.setItem(6, CraftItemStack.asNMSCopy(ingredients.get(6)));
        ic.setItem(7, CraftItemStack.asNMSCopy(ingredients.get(7)));
        ic.setItem(8, CraftItemStack.asNMSCopy(ingredients.get(8)));

        Optional<RecipeCrafting> optional = ((CraftServer) Bukkit.getServer()).getHandle().getServer().getCraftingManager().craft(Recipes.CRAFTING, ic, null);
        if (optional.isPresent()) {
            RecipeCrafting recipecrafting = optional.get();
            net.minecraft.server.v1_16_R1.ItemStack itemStack = recipecrafting.a(ic);
            return itemStack.asBukkitCopy();
        }
        return null;
    }

    public static void openCraftingInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "§e§lCrafting");
        for (int i = 0; i < inv.getSize(); i++) {
            if (craftingSlots.contains(i) || bottomBarSlots.contains(i)) continue;
            if (i == 24) continue;
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
        }
        inv.setItem(49, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§bRecipes").build());
        for (int i : bottomBarSlots)
            inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build());
        player.openInventory(inv);
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onOpen(InventoryOpenEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        Main.getInstance().getUtilLists().craftingTables.put(event.getPlayer().getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            Player player = (Player) event.getPlayer();
            if (player.getOpenInventory().getTitle().equals("§e§lCrafting")) {
                update(player.getOpenInventory());
            } else {
                Bukkit.getScheduler().cancelTask(Main.getInstance().getUtilLists().craftingTables.get(player.getUniqueId()));
            }
        }, 2L, 2L));
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        ItemStack item = event.getCurrentItem();
        if (event.getSlot() == 24 && view.getItem(24) != null) {
            for (int i : craftingSlots)
                view.setItem(i, null);
            for (int i : bottomBarSlots)
                view.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build());
        }
        if (item != null && (item.getItemMeta().getDisplayName().equals("§bRecipes") || (item.getType().equals(Material.BLACK_STAINED_GLASS_PANE) || item.getType().equals(Material.RED_STAINED_GLASS_PANE) || item.getType().equals(Material.LIME_STAINED_GLASS_PANE)) && event.getCurrentItem().getItemMeta().getDisplayName().equals(" "))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        update(view);
    }

    private ItemStack update(InventoryView view) {
        final List<ItemStack> ingredients = new ArrayList<>();
        for (int i : craftingSlots)
            ingredients.add(view.getItem(i));
        ItemStack result = craft(ingredients);
        view.setItem(24, result);
        Material mat = result == null ? Material.RED_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE;
        for (int i : bottomBarSlots)
            view.setItem(i, new ItemBuilder(mat).setNoName().build());
        return result;
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("§e§lCrafting")) return;
        for (int i : craftingSlots) {
            ItemStack item = event.getView().getItem(i);
            if (item == null) continue;
            event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
            Main.getInstance().getUtilLists().craftingTables.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = event.getClickedBlock();
        if (block.getType().equals(Material.CRAFTING_TABLE)) {
            event.setCancelled(true);
            openCraftingInventory(event.getPlayer());
        }
    }

}
