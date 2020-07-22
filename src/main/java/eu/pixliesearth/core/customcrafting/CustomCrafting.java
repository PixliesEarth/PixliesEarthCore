package eu.pixliesearth.core.customcrafting;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.CustomItems;
import eu.pixliesearth.utils.ItemBuilder;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomCrafting implements Listener {

    static final List<Integer> craftingSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    static final List<Integer> bottomBarSlots = Arrays.asList(45, 46, 47, 48, 50, 51, 52, 53);

    private final Main instance = Main.getInstance();

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
        ItemStack item = event.getCurrentItem();
        boolean took = false;
        if (inventory != null && event.getSlot() == 24 && item != null) {
            if (inventory.equals(view.getTopInventory())) {
                took = true;
            }
        }
        if (inventory != null) {
            if (bottomBarSlots.contains(event.getSlot()) || (item != null && (item.getType().equals(Material.RED_STAINED_GLASS_PANE) || item.getType().equals(Material.LIME_STAINED_GLASS_PANE) || item.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) && item.getItemMeta().getDisplayName().equals(" ")))
                event.setCancelled(true);
            if (event.getSlot() == 49)
                event.setCancelled(true);
        }
        if (took) {
            @NonNull CustomRecipe recipe = getRecipe(inventory);
            for (int i : craftingSlots) {
                ItemStack slot = inventory.getItem(i);
                if (slot == null) continue;
                switch (i) {
                    case 10:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS1().getAmount()).build();
                        break;
                    case 11:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS2().getAmount()).build();
                        break;
                    case 12:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS3().getAmount()).build();
                        break;
                    case 19:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS4().getAmount()).build();
                        break;
                    case 20:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS5().getAmount()).build();
                        break;
                    case 21:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS6().getAmount()).build();
                        break;
                    case 28:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS7().getAmount()).build();
                        break;
                    case 29:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS8().getAmount()).build();
                        break;
                    case 30:
                        slot = new ItemBuilder(slot).setAmount(slot.getAmount() - recipe.getS9().getAmount()).build();
                        break;
                }
                inventory.setItem(i, slot);
            }
            event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().getWorld().dropItemNaturally(event.getWhoClicked().getLocation(), item);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onOpen(InventoryOpenEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        Player player = (Player) event.getPlayer();
        instance.getUtilLists().craftingTables.put(event.getPlayer().getUniqueId(), Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            if (!player.isOnline()) {
                Bukkit.getScheduler().cancelTask(instance.getUtilLists().craftingTables.get(player.getUniqueId()));
                return;
            }
            final InventoryView inventory = player.getOpenInventory();
            if (!inventory.getTitle().equals("§e§lCrafting")) {
                Bukkit.getScheduler().cancelTask(instance.getUtilLists().craftingTables.get(player.getUniqueId()));
                return;
            }
            CustomRecipe recipe = getRecipe(inventory.getTopInventory());
            if (recipe != null) {
                for (int i : bottomBarSlots)
                    inventory.getTopInventory().setItem(i, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§a§lRIGHT RECIPE").build());
            } else {
                for (int i : bottomBarSlots)
                    inventory.getTopInventory().setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lWRONG RECIPE").build());
            }
            ItemStack result = recipe == null ? null : recipe.getResult();
            inventory.getTopInventory().setItem(24, result);
        }, 5, 5));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event) {
        InventoryView view = event.getView();
        if (!view.getTitle().equals("§e§lCrafting")) return;
        for (int i : craftingSlots) {
            ItemStack item = event.getInventory().getItem(i);
            if (item != null)
                event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
        }
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1, 1);
        Bukkit.getScheduler().cancelTask(instance.getUtilLists().craftingTables.get(event.getPlayer().getUniqueId()));
        instance.getUtilLists().craftingTables.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteraction(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block right = block.getRelative(BlockFace.EAST);
        Block left = block.getRelative(BlockFace.WEST);
        if (block.getType().equals(Material.CRAFTING_TABLE) && right.getType().equals(Material.BOOKSHELF) && left.getType().equals(Material.BOOKSHELF)) {
            event.setCancelled(true);
            open(event.getPlayer());
        }
    }

    private CustomRecipe getRecipe(final Inventory inventory) {
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
            return recipe;
        }
        return null;
    }

}
