package eu.pixliesearth.core.machines.kiln;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.core.machines.cargo.OutputNode;
import eu.pixliesearth.localization.Lang;
import eu.pixliesearth.nations.entities.chunk.NationChunk;
import eu.pixliesearth.nations.entities.nation.Nation;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.bukkit.Material.*;

public class Kiln extends Machine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0")).setDisplayName("§c§lKiln").build();
    private Inventory inventory;
    private int fuel;

    public Kiln(String id, Location location) {
        super(id, location, MachineType.KILN, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
        fuel = 0;
    }

    public String getTitle() {
        return "§c§lKiln";
    }

    public Kiln(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, int fuel) {
        super(id, location, MachineType.KILN, item, timer, armorStand, wantsToCraft);
        this.fuel = fuel;
    }

    @Override
    public void save() throws IOException {
        File file = new File("plugins/PixliesEarthCore/machines", id + ".yml");

        if (!file.exists())
            file.createNewFile();

        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        conf.set("location", location);
        conf.set("type", type.name());
        conf.set("fuel", fuel);
        if (wantsToCraft != null) {
            conf.set("wantsToCraft", wantsToCraft.name());
        } else {
            conf.set("wantsToCraft", null);
        }
        conf.set("item", item);
        if (timer != null) {
            conf.set("timer.expiry", timer.getExpiry());
            conf.set("timer.ended", timer.isEnded());
        } else {
            conf.set("timer", null);
        }
        conf.set("holo.location", armorStand.getLocation());
        conf.set("holo.text", getTitle());
        conf.save(file);
    }

    public void reopen(Player player) {
        inventory = null;
        wantsToCraft = null;
        timer = null;
        open(player);
    }

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
        StaticPane pane = new StaticPane(1, 1, 9, 4);
        int x = 0;
        int y = 0;
        for (MachineCraftable item : MachineCraftable.values()) {
            if (!item.type.equals(type)) continue;
            if (x + 1 > 8) {
                x = 0;
                y++;
            }
            ItemBuilder iconBuilder = new ItemBuilder(item.icon);
            if (item.eraNeeded.canAccess(nation)) {
                iconBuilder.setDisplayName("§a§l" + item.icon.getItemMeta().getDisplayName());
                iconBuilder.addLoreLine("§7Era needed: §a" + item.eraNeeded.getName());
            } else {
                iconBuilder.setDisplayName("§c§l" + item.icon.getItemMeta().getDisplayName());
                iconBuilder.addLoreLine("§7Era needed: §c" + item.eraNeeded.getName());
            }
            pane.addItem(new GuiItem(iconBuilder.build(), event -> {
                event.setCancelled(true);
                if (item.eraNeeded.canAccess(nation))
                    openItemCrafter(player, item);
            }), x, y);
            x++;
        }
        gui.addPane(pane);
        gui.show(player);
    }

    private void openItemCrafter(Player player, MachineCraftable item) {
        wantsToCraft = item;
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 9 * 6, getTitle() + " §8| " + item.icon.getItemMeta().getDisplayName());
            for (int i = 0; i < inventory.getSize(); i++) {
                if (craftSlots.contains(i) || resultSlots.contains(i)) continue;
                inventory.setItem(i, new ItemBuilder(BLACK_STAINED_GLASS_PANE).setNoName().build());
            }
            int ingredientsAdded = 0;
            for (ItemStack ingredients : item.ingredients) {
                inventory.setItem(45 + ingredientsAdded, ingredients);
                ingredientsAdded++;
            }
        }
        inventory.setItem(53, new ItemBuilder(BARRIER).setDisplayName("§c§lBack").build());
        update();
        player.openInventory(inventory);
    }

    private void update() {
        if (fuel == 0) {
            int radius = 1;
            final Block block = location.getBlock();
            for (int x = -(radius); x <= radius; x++) {
                for (int z = -(radius); z <= radius; z++) {
                    final Block relative = block.getRelative(x, 0, z);
                    if (instance.getUtilLists().machines.containsKey(relative.getLocation()) && instance.getUtilLists().machines.get(relative.getLocation()) instanceof InputNode) {
                        InputNode in = (InputNode) instance.getUtilLists().machines.get(relative.getLocation());
                        if (timer == null) {
                            boolean take = in.takeItem(new ItemStack(MAGMA_BLOCK));
                            if (take) {
                                fuel = 100;
                            }
                        }
                        break;
                    }
                }
            }
        }
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
                    fuel -= 25;
                }
            }
        }
        int radius = 1;
        final Block block = location.getBlock();
        boolean stop = false;
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
                                    break;
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
     * @param p_progressIndex: Position of pane in inventory row.
     * @param p_timeToCompleteMilliseconds: Time left for the recipe to be completed.
     */
    private void setProgressBarColourByIndex
    ( int p_progressIndex, long p_timeToCompleteMilliseconds )
    {
        // The progress bar is only 9 inventory cells long.
        int numberOfCells = 9;

        // Change a pane to green when the time decreases, time / numberOfCells.
        switch ( p_progressIndex )
        {
            case 36:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / numberOfCells) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 37:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 1) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 38:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 2) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 39:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 3) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 40:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 4) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 41:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 5) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 42:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 6) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 43:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 7) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
            case 44:
                if ( p_timeToCompleteMilliseconds >= (p_timeToCompleteMilliseconds / ( numberOfCells - 8) ) )
                    inventory.setItem(p_progressIndex,
                            new ItemBuilder(GREEN_STAINED_GLASS_PANE)
                                    .setDisplayName("§3§lLeft")
                                    .addLoreLine("§b" + timer.getRemainingAsString())
                                    .build());
                break;
        }
    }

    private void setProgressBar(boolean matching) {
        if (timer != null && timer.getRemaining() > 0) {
            for (int i : progressSlots) {
                setProgressBarColourByIndex(i, timer.getRemaining());
            }
        } else {
            if (!matching)
                for (int i : progressSlots)
                    inventory.setItem(i, new ItemBuilder(RED_STAINED_GLASS_PANE).setDisplayName("§c§lWrong ingredients").build());
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

    private void addResult(ItemStack result) {
        for (int i : resultSlots) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, result);
            break;
        }
    }

    private boolean canAddResult() {
        for (int i : resultSlots)
            if (inventory.getItem(i) == null) return true;
        return false;
    }

    @Override
    public void remove() {
        File file = new File("plugins/PixliesEarthCore/machines/" + id + ".yml");
        instance.getUtilLists().machines.remove(location);
        armorStand.delete();
        if (!file.exists()) return;
        file.delete();
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
