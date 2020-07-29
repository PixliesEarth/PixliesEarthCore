package eu.pixliesearth.core.machines;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.Orientable;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.PercentageBar;
import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import net.minecraft.server.v1_16_R1.BlockWorkbench;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CarpentryMill extends Machine {

    public CarpentryMill(Location location) {
        super("Carpentry Mill", location);
    }

    private Gui gui;
    private long progress;
    private MachineProcess process;
    private MachineCraftable wantsToCraft;
    private StaticPane craftPane;
    private StaticPane resultPane;
    private StaticPane bottomBar;
    private PercentageBar percentageBar;
    private boolean prepared = false;

    private void openItem(MachineCraftable item, Player player) {
        Bukkit.getScheduler().runTask(instance, () -> {
            //TODO REMAKE
            gui = new Gui(instance, 6, "§b§l" + item.menuItem.getI18NDisplayName());
            wantsToCraft = item;
            StaticPane background = new StaticPane(0, 0, 9, 4);
            background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
            bottomBar = new StaticPane(0, 5, 9, 1);
            bottomBar.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
            int ix = 0;
            for (ItemStack ingredient : item.ingredients) {
                bottomBar.addItem(new GuiItem(ingredient, event -> event.setCancelled(true)), ix, 0);
                ix++;
            }
            craftPane = new StaticPane(1, 1, 3, 3);
            resultPane = new StaticPane(1, 1, 3, 3);
            gui.addPane(background);
            gui.addPane(craftPane);
            gui.addPane(resultPane);
            percentageBar = new PercentageBar(0, 4, 9, 1);
            percentageBar.setBackgroundItem(new GuiItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true)));
            percentageBar.setFillItem(new GuiItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§7Progress: §a").build()));
            percentageBar.setOrientation(Orientable.Orientation.HORIZONTAL);
            progress = 0;
            percentageBar.setPercentage(progress);
            gui.addPane(percentageBar);
            gui.show(player);
            prepared = true;
        });
    }

    private boolean tryCraft() {
        List<GuiItem> ingredients = new ArrayList<>(craftPane.getItems());
        ingredients.removeIf(item -> item.getItem().getI18NDisplayName().equals("§a§lCraft"));
        return ingredients.containsAll(wantsToCraft.ingredients);
    }

    private void update() {
        if (process != null) {
            progress = System.currentTimeMillis() * 100 / process.getTimer().getExpiry();
            percentageBar.setPercentage(progress);
            gui.addPane(percentageBar);
            if (process.getTimer().getRemaining() <= 0) {
                process = null;
                progress = 0;
                craftPane.clear();
                int cx = 0;
                int cy = 0;
                for (ItemStack result : wantsToCraft.result) {
                    resultPane.addItem(new GuiItem(result), cx, cy);
                    if (cx + 1 > 2) {
                        cx = 0;
                        cy++;
                    } else {
                        cx++;
                    }
                }
                bottomBar.addItem(new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cWrong ingredients").build(), event -> event.setCancelled(true)), 8, 0);
                gui.addPane(bottomBar);
                gui.addPane(craftPane);
                gui.addPane(resultPane);
            }
        } else {
            boolean canCraft = tryCraft();
            if (canCraft) {
                bottomBar.addItem(new GuiItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§a§lProcess").build(), event -> {
                    process = new MachineProcess(wantsToCraft.ingredients, new Timer(4000));
                    craftPane.clear();
                    craftPane.fillWith(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setNoName().build(), event1 -> event1.setCancelled(true));
                }), 0, 8);
                gui.addPane(craftPane);
                gui.addPane(bottomBar);
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Block center = event.getClickedBlock();
        if (center == null) return;
        Block east = center.getRelative(-1, 0, 0);
        Block west = center.getRelative(1, 0, 0);
        Block north = center.getRelative(0, 1, 0);
        Block south = center.getRelative(0, -1,0);
        Block north_west = north.getRelative(1, 0, 0);
        Block north_east = north.getRelative(-1, 0, 0);
        Block south_west = south.getRelative(1, 0, 0);
        Block south_east = south.getRelative(-1, 0, 0);
        if (center.getType().equals(Material.CRAFTING_TABLE)) {
            System.out.println("Triggered 1");
            if (!instance.getUtilLists().machines.containsKey(center.getLocation())) instance.getUtilLists().machines.put(center.getLocation(), this);
            event.setCancelled(true);
            openItem(MachineCraftable.CUT_WOOD, event.getPlayer());
        }
    }

    @Override
    public void tick() {
        if (prepared) {
            if (process != null && process.getTimer().getRemaining() <= 0)
                process = null;
            gui.update();
            if (wantsToCraft != null) update();
        }
    }

}
