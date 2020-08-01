package eu.pixliesearth.core.machines;

import com.github.stefvanschie.inventoryframework.Gui;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CarpentryMill extends Machine {

    private MachineProcess task;
    private CMItem wantsToCraft;
    private Inventory inventory;

    public CarpentryMill(Location location) {
        super(location, Material.CRAFTING_TABLE, blockFaceMap());
    }

    public static Map<BlockFace, Material> blockFaceMap() {
        Map<BlockFace, Material> map = new HashMap<>();
        map.put(BlockFace.WEST, Material.OAK_LOG);
        map.put(BlockFace.EAST, Material.OAK_LOG);
        return map;
    }

    private final List<Integer> craftSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    private final List<Integer> resultSlots = Arrays.asList(14, 15, 16, 23, 24, 25, 32, 33, 34);

    @Override
    public void open(Player player) {
        if (task != null) {
            openItemCrafter(player, wantsToCraft);
            return;
        }
        Gui gui = new Gui(instance, 5, "§b§lCarpentry Mill");

        gui.show(player);
    }

    private void openItemCrafter(Player player, CMItem item) {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 9 * 6, "§b§lCarpentry Mill §8| " + item.displayName);
            for (int i = 0; i < inventory.getSize(); i++) {
                if (craftSlots.contains(i) || resultSlots.contains(i)) continue;
                inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
            }
            int ingredientsAdded = 0;
            for (ItemStack ingredients : item.ingredients) {
                inventory.setItem(45 + ingredientsAdded, ingredients);
                ingredientsAdded++;
            }
        }
        update();
        player.openInventory(inventory);
    }

    private void update() {
        if (inventory == null) return;
        if (task != null) {
            if (task.getTimer().getRemaining() <= 0) {
                task = null;
                inventory.setItem(53, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName("§bProcessing").addLoreLine("§3§l" + task.getTimer().getRemainingAsString()).build());
                int i = 0;
                for (ItemStack result : wantsToCraft.results) {
                    inventory.setItem(resultSlots.get(i), result);
                    i++;
                }
                return;
            }
            inventory.setItem(53, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName("§bProcessing").addLoreLine("§3§l" + task.getTimer().getRemainingAsString()).build());
        } else {
            boolean matching = recipeMatching(inventory);
            if (matching) {
                inventory.setItem(53, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§aClick to start").build());
            } else {
                inventory.setItem(53, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cWrong ingredients").build());
            }
        }
    }

    private boolean recipeMatching(Inventory inv) {
        List<ItemStack> items = new ArrayList<>();
        for (int i : craftSlots) {
            ItemStack itemOnSlot = inv.getItem(i);
            if (itemOnSlot == null) continue;
            items.add(inv.getItem(i));
        }
        return items.containsAll(wantsToCraft.ingredients);
    }

    @Override
    public void tick() {
        update();
    }

    enum CMItem {

        CUT_WOOD("§aCut Wood", Arrays.asList(new ItemStack(Material.OAK_LOG, 64)), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64)));

        public String displayName;
        public List<ItemStack> ingredients;
        public List<ItemStack> results;

        CMItem(String displayName, List<ItemStack> ingredients, List<ItemStack> results) {
            this.displayName = displayName;
            this.ingredients = ingredients;
            this.results = results;
        }

    }

}
