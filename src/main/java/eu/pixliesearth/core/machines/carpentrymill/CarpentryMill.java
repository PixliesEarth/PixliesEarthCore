package eu.pixliesearth.core.machines.carpentrymill;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.machines.MachineProcess;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CarpentryMill extends Machine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/83d47199d034fae71e5c7ef1e12bf9f1adbb88c22ad4b0e9453abf8cee5c350b")).setDisplayName("§b§lCarpentry Mill").build();
    public static final List<Integer> craftSlots = Arrays.asList(0, 1, 2, 3, 9, 18, 27, 10, 11, 12, 19, 20, 21, 28, 29, 30);
    public static final List<Integer> resultSlots = Arrays.asList(5, 6, 7, 8, 17, 26, 35, 14, 15, 16, 23, 24, 25, 32, 33, 34);
    public static final List<Integer> progressSlots = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44);

    private MachineProcess task;
    private CMItem wantsToCraft;
    private Inventory inventory;
    public Hologram armorStand;

    public CarpentryMill(Location location) {
        super(location, item);
        Location toSpawn = location.clone();
        toSpawn.setY(toSpawn.getY() + 1D);
        toSpawn.setX(toSpawn.getX() + 0.5);
        toSpawn.setZ(toSpawn.getZ() + 0.5);
        armorStand = HologramsAPI.createHologram(instance, toSpawn);
        armorStand.appendTextLine("§b§lCarpentry Mill");
    }

    public void reopen(Player player) {
        inventory = null;
        wantsToCraft = null;
        task = null;
        open(player);
    }

    @Override
    public void open(Player player) {
        if (inventory != null) {
            player.openInventory(inventory);
            return;
        }
        Gui gui = new Gui(instance, 6, "§b§lCarpentry Mill");
        OutlinePane outline = new OutlinePane(0, 0, 9, 6);
        outline.addItem(new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true)));
        outline.setRepeat(true);
        gui.addPane(outline);
        StaticPane pane = new StaticPane(1, 1, 9, 4);
        int x = 0;
        int y = 0;
        for (CMItem item : CMItem.values()) {
            if (x + 1 > 8) {
                x = 0;
                y++;
            }
            pane.addItem(new GuiItem(new ItemBuilder(item.icon).addLoreLine("§7Time: §3" + item.seconds + "§bsec").build(), event -> { event.setCancelled(true); openItemCrafter(player, item); }), x, y);
            x++;
        }
        gui.addPane(pane);
        gui.show(player);
    }

    private void openItemCrafter(Player player, CMItem item) {
        wantsToCraft = item;
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 9 * 6, "§b§lCarpentry Mill §8| " + item.icon.getItemMeta().getDisplayName());
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
        inventory.setItem(53, new ItemBuilder(Material.BARRIER).setDisplayName("§c§lBack").build());
        update();
        player.openInventory(inventory);
    }

    private void update() {
        if (inventory == null) return;
        if (task != null) {
            if (task.getTimer().getRemaining() <= 0) {
                task = null;
                int i = 0;
                for (ItemStack result : wantsToCraft.results) {
                    inventory.setItem(resultSlots.get(i), result);
                    i++;
                }
                return;
            }
            setProgressBar(true);
        } else {
            boolean matching = recipeMatching(inventory);
            setProgressBar(matching);
            if (matching) {
                task = new MachineProcess(new Timer(wantsToCraft.seconds * 1000));
                for (int i : craftSlots)
                    inventory.clear(i);
            }
        }
    }

    private void setProgressBar(boolean matching) {
        if (task != null && task.getTimer().getRemaining() > 0) {
            for (int i : progressSlots)
                inventory.setItem(i, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName("§3§lLeft").addLoreLine("§b" + task.getTimer().getRemainingAsString()).build());
        } else {
            if (!matching)
                for (int i : progressSlots)
                    inventory.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lWrong ingredients").build());
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

        CUT_WOOD(new ItemBuilder(Material.OAK_LOG).setDisplayName("Cut Wood").addLoreLine("§a32 §7oak-log > §a4x64 §7oak-planks").build(), Arrays.asList(new ItemStack(Material.OAK_LOG, 32)), Arrays.asList(new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64), new ItemStack(Material.OAK_PLANKS, 64)), 4);

        public ItemStack icon;
        public List<ItemStack> ingredients;
        public List<ItemStack> results;
        public int seconds;

        CMItem(ItemStack icon, List<ItemStack> ingredients, List<ItemStack> results, int seconds) {
            this.icon = icon;
            this.ingredients = ingredients;
            this.results = results;
            this.seconds = seconds;
        }

    }

}
