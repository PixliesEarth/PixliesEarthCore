package eu.pixliesearth.core.machines.autocrafters;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import eu.pixliesearth.core.files.JSONFile;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.core.machines.cargo.OutputNode;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.*;

public class AutoCrafterMachine extends Machine {

    public AutoCrafterMachine(String id, Location location, MachineType type, ItemStack item, Timer timer, Hologram armorStand, MachineCraftable wantsToCraft) {
        super(id, location, type, item, timer, armorStand, wantsToCraft);
    }

    public AutoCrafterMachine(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
    }

    protected Inventory inventory;

    public void reopen(Player player) {
        inventory = null;
        wantsToCraft = null;
        timer = null;
        open(player);
    }

    protected void beforeUpdate() { }

    @Override
    public void open(Player player) {
        NationChunk nc = NationChunk.get(location.getChunk());
        if (nc == null) {
            player.sendActionBar(Lang.MACHINES_NEED_TO_BE_IN_CLAIMED_TERRITORY.get(player));
            return;
        }
        if (inventory != null) {
            player.openInventory(inventory);
            return;
        }
        Nation nation = nc.getCurrentNation();
        Gui gui = new Gui(instance, 6, getTitle());
        OutlinePane outline = new OutlinePane(0, 0, 9, 6);
        outline.addItem(new GuiItem(new ItemBuilder(BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true)));
        outline.setRepeat(true);
        gui.addPane(outline);
        PaginatedPane pane = new PaginatedPane(1, 1, 7, 4);
        List<GuiItem> guiItems = new ArrayList<>();
        for (MachineCraftable item : MachineCraftable.values()) {
            if (!item.type.equals(type)) continue;
            ItemBuilder iconBuilder = new ItemBuilder(item.icon);
            iconBuilder.addLoreLine("§f§lLEFT §7click to open crafter");
            iconBuilder.addLoreLine("§7Time: §b" + item.seconds + " §7second(s)");
            if (item.eraNeeded.canAccess(nation)) {
                iconBuilder.setDisplayName(item.icon.hasItemMeta() ? "§a§l" + item.icon.getItemMeta().getDisplayName() : "§a§l" + item.icon.getI18NDisplayName());
                iconBuilder.addLoreLine("§7Era needed: §a" + item.eraNeeded.getName());
            } else {
                iconBuilder.setDisplayName(item.icon.hasItemMeta() ? "§c§l" + item.icon.getItemMeta().getDisplayName() : "§c§l" + item.icon.getI18NDisplayName());
                iconBuilder.addLoreLine("§7Era needed: §c" + item.eraNeeded.getName());
            }
            guiItems.add(new GuiItem(iconBuilder.build(), event -> {
                event.setCancelled(true);
                if (event.getClick() != ClickType.LEFT) return;
                if (item.eraNeeded.canAccess(nation))
                    openItemCrafter(player, item);
            }));
        }
        pane.populateWithGuiItems(guiItems);
        StaticPane hotBar = new StaticPane(0, 5, 9, 1);
        hotBar.addItem(new GuiItem(new ItemBuilder(HEART_OF_THE_SEA).setDisplayName("§b§lNext Page").build(), event -> {
            event.setCancelled(true);
            try {
                pane.setPage(pane.getPage() + 1);
                gui.addPane(pane);
                gui.show(player);
            } catch (Exception ignore) { }
        }), 8, 0);
        hotBar.addItem(new GuiItem(new ItemBuilder(HEART_OF_THE_SEA).setDisplayName("§b§lLast Page").build(), event -> {
            event.setCancelled(true);
            try {
                pane.setPage(pane.getPage() - 1);
                gui.addPane(pane);
                gui.show(player);
            } catch (Exception ignore) { }
        }), 0, 0);
        gui.addPane(hotBar);
        gui.addPane(pane);
        gui.show(player);
    }

    protected void openItemCrafter(Player player, MachineCraftable item) {
        wantsToCraft = item;
        if (inventory == null) {
            String itemName = item.icon.hasItemMeta() ? item.icon.getItemMeta().getDisplayName() : item.icon.getI18NDisplayName();
            inventory = Bukkit.createInventory(null, 9 * 6, getTitle() + " §8| " + itemName);
            for (int i = 0; i < inventory.getSize(); i++) {
                if (craftSlots.contains(i) || resultSlots.contains(i)) continue;
                inventory.setItem(i, new ItemBuilder(BLACK_STAINED_GLASS_PANE).setNoName().build());
            }
            ItemBuilder recipeBuilder = new ItemBuilder(Material.BOOK).setDisplayName("§f§lRECIPE");
            for (ItemStack ingredient : item.ingredients)
                recipeBuilder.addLoreLine(ingredient.hasItemMeta() ? "§b§l" + ingredient.getAmount() + " §7" + ingredient.getItemMeta().getDisplayName() : "§b§l" + ingredient.getAmount() + " §7" + ingredient.getI18NDisplayName());
            inventory.setItem(45, recipeBuilder.build());
        }
        inventory.setItem(53, new ItemBuilder(BARRIER).setDisplayName("§c§lBack").build());
        update();
        player.openInventory(inventory);
    }

    protected void update() {
        beforeUpdate();
        if (inventory == null) return;
        if (timer != null) {
            if (timer.getRemaining() <= 0) {
                timer = null;
                for (ItemStack result : wantsToCraft.results) {
                    addResult(result);
                }
            }
            setProgressBar(true);
        } else {
            boolean matching = recipeMatching(inventory);
            setProgressBar(matching);
            if (matching) {
                if (canAddResult()) {
                    timer = new Timer(wantsToCraft.seconds * 1000);
                    for (ItemStack ingredient : wantsToCraft.ingredients) {
                        Methods.removeRequiredAmountWithinBound(ingredient, inventory, craftSlots);
                    }
                }
            }
        }
        int radius = 1;
        final Block block = location.getBlock();
        boolean stop = false;
        x:
        for (int x = -(radius); x <= radius; x++) {
            for (int z = -(radius); z <= radius; z++) {
                final Block relative = block.getRelative(x, 0, z);
                if (instance.getUtilLists().machines.containsKey(relative.getLocation()) && instance.getUtilLists().machines.get(relative.getLocation()) instanceof InputNode) {
                    InputNode in = (InputNode) instance.getUtilLists().machines.get(relative.getLocation());
                    if (timer == null) {
                        for (ItemStack ingredient : wantsToCraft.ingredients) {
                            boolean take = in.takeItem(ingredient);
                            if (take) {
                                for (int i : craftSlots) {
                                    if (inventory.getItem(i) != null) continue;
                                    inventory.setItem(i, ingredient);
                                    stop = true;
                                    break x;
                                }
                                if (!stop) in.addItem(ingredient);
                            }
                        }
                    }
                }
                if (instance.getUtilLists().machines.containsKey(relative.getLocation()) && instance.getUtilLists().machines.get(relative.getLocation()) instanceof OutputNode) {
                    OutputNode out = (OutputNode) instance.getUtilLists().machines.get(relative.getLocation());
                    for (int i : resultSlots) {
                        if (inventory.getItem(i) == null) continue;
                        boolean add = out.addItem(inventory.getItem(i));
                        if (!add) break;
                        inventory.clear(i);
                        stop = true;
                    }
                }
            }
        }
    }

    /*
     * Used to change the colour of the progressbar panels.
     * @param p_timeToCompleteMilliseconds: Time left for the recipe to be completed.
     */
    protected void setProgressBarColourByIndex
    (long p_timeToCompleteMilliseconds )
    {
        // The progress bar is only 9 inventory cells long.
        int numberOfCells = 9;
        long timeTaken = p_timeToCompleteMilliseconds - timer.getRemaining();
        int framesToFill = ( int )(
                ( timeTaken ) / ( p_timeToCompleteMilliseconds / numberOfCells )
        );

        // Debug
        Bukkit.broadcastMessage(
                "Time Taken: " + timeTaken + " | Frames to fill: " + framesToFill
        );

        Bukkit.broadcastMessage(
                "Time to complete: " + p_timeToCompleteMilliseconds + " | Time remaining: " + timer.getRemaining()
        );

        // Change a pane to green when framesToFill is a number from 1 to 9.
        switch ( framesToFill )
        {
            case 1:
                inventory.setItem(36,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 2:
                inventory.setItem(37,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 3:
                inventory.setItem(38,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 4:
                inventory.setItem(39,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 5:
                inventory.setItem(40,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 6:
                inventory.setItem(41,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 7:
                inventory.setItem(42,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 8:
                inventory.setItem(43,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
            case 9:
                inventory.setItem(44,
                        new ItemBuilder(LIME_STAINED_GLASS_PANE)
                                .setDisplayName("§3§lLeft")
                                .addLoreLine("§b" + timer.getRemainingAsString())
                                .build());
                break;
        }
    }

    protected void setProgressBar(boolean matching) {
        // The time required for the recipe to be completed.
        if (timer != null && timer.getRemaining() > 0) {
            final long long_TimeToComplete = timer.getRemaining();
            for (@SuppressWarnings("unused") int ignored : progressSlots)
                setProgressBarColourByIndex(long_TimeToComplete);
        } else {
            if (!matching)
                for (int i : progressSlots)
                    inventory.setItem(i, new ItemBuilder(RED_STAINED_GLASS_PANE).setDisplayName("§c§lWrong ingredients").build());
        }
    }

    protected boolean recipeMatching(Inventory inv) {
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

    protected void addResult(ItemStack result) {
        for (int i : resultSlots) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, result);
            break;
        }
    }

    protected boolean canAddResult() {
        for (int i : resultSlots)
            if (inventory.getItem(i) == null) return true;
        return false;
    }

    @Override
    public void remove() {
    	JSONFile file = new JSONFile(getMachineSavePath(), id);
        instance.getUtilLists().machines.remove(location);
        armorStand.delete();
        file.deleteFile();
        if (inventory != null) {
            for (int i : craftSlots) {
                if (inventory.getItem(i) == null) continue;
                location.getWorld().dropItemNaturally(location, inventory.getItem(i));
            }
            for (int i : resultSlots) {
                if (inventory.getItem(i) == null) continue;
                location.getWorld().dropItemNaturally(location, inventory.getItem(i));
            }
        }
    }

}
