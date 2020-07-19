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
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CraftingManager implements Listener {

    static final List<Integer> craftingSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    static final List<Integer> bottomBarSlots = Arrays.asList(45, 46, 47, 48, 49, 50, 51, 52, 53);

    static private ContainerWorkbench containerWorkbench;
    static private InventoryCrafting ic;

/*    public static ItemStack craft(List<ItemStack> ingredients) {
*//*        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while(recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            if(recipe instanceof ShapelessRecipe) {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                if (ingredients.equals(shapelessRecipe.getIngredientList()))
                    return shapelessRecipe.getResult();
            } else if(recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                boolean match = true;
                StringBuilder recipeWholeBuilder = new StringBuilder();
                String recipeWhole = recipeWholeBuilder.toString();
                for (String s : shapedRecipe.getShape()) recipeWholeBuilder.append(s);
                for (int i = 0; i < recipeWhole.length(); i++) {
                    char ch = recipeWhole.charAt(i);
                    if (!shapedRecipe.getIngredientMap().get(ch).equals(ingredients.get(i))) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return shapedRecipe.getResult();
                }
            }
        }*//*
    }*/

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
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
        }
        for (int i : bottomBarSlots)
            inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build());
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        ItemStack item = event.getCurrentItem();
        update(view, event.getSlot());
        if (item != null && (item.getType().equals(Material.BARRIER) || (item.getType().equals(Material.BLACK_STAINED_GLASS_PANE) || item.getType().equals(Material.RED_STAINED_GLASS_PANE) || item.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) && event.getCurrentItem().getItemMeta().getDisplayName().equals(" "))) {
            event.setCancelled(true);
        }
    }

    private void update(InventoryView view, int slot) {
        final List<ItemStack> ingredients = new ArrayList<>();
        for (int i : craftingSlots)
            ingredients.add(view.getItem(i));
        ItemStack result = craft(ingredients);
        view.setItem(24, result);
        Material mat = result == null ? Material.RED_STAINED_GLASS_PANE : Material.GREEN_STAINED_GLASS_PANE;
        for (int i : bottomBarSlots)
            view.setItem(i, new ItemBuilder(mat).setNoName().build());
        if (result != null) {
            if (slot == 24) {
                for (int i : craftingSlots)
                    view.setItem(i, null);
                for (int i : bottomBarSlots)
                    view.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build());
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("§e§lCrafting")) return;
        for (int i : craftingSlots) {
            if (event.getView().getItem(i) == null) continue;
            event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), event.getView().getItem(i));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        if (block.getType().equals(Material.CRAFTING_TABLE)) {
            event.setCancelled(true);
            openCraftingInventory(event.getPlayer());
        }
    }

}
