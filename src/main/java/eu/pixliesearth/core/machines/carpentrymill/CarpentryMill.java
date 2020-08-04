package eu.pixliesearth.core.machines.carpentrymill;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CarpentryMill extends Machine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/83d47199d034fae71e5c7ef1e12bf9f1adbb88c22ad4b0e9453abf8cee5c350b")).setDisplayName("§b§lCarpentry Mill").build();
    private Inventory inventory;

    public CarpentryMill(String id, Location location) {
        super(id, location, MachineType.CARPENTRY_MILL, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
    }

    public String getTitle() {
        return "§b§lCarpentry Mill";
    }

    public CarpentryMill(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft) {
        super(id, location, MachineType.CARPENTRY_MILL, item, timer, armorStand, wantsToCraft);
    }

    public void reopen(Player player) {
        inventory = null;
        wantsToCraft = null;
        timer = null;
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
        for (MachineCraftable item : MachineCraftable.values()) {
            if (!item.type.equals(MachineType.CARPENTRY_MILL)) continue;
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

    private void openItemCrafter(Player player, MachineCraftable item) {
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
        if (timer != null) {
            if (timer.getRemaining() <= 0) {
                timer = null;
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
                timer = new Timer(wantsToCraft.seconds * 1000);
                for (int i : craftSlots)
                    inventory.clear(i);
            }
        }
    }

    private void setProgressBar(boolean matching) {
        if (timer != null && timer.getRemaining() > 0) {
            for (int i : progressSlots)
                inventory.setItem(i, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName("§3§lLeft").addLoreLine("§b" + timer.getRemainingAsString()).build());
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

}
