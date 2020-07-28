package eu.pixliesearth.core.machines;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.PercentageBar;
import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class CarpentryMill extends Machine {

    public CarpentryMill(Location location) {
        super("Carpentry Mill", location);
        register(this);
    }

    private Gui gui;
    private long progress;
    private MachineProcess process;
    private MachineCraftable wantsToCraft;
    private StaticPane craftPane;

    private GuiItem confirm = new GuiItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§a§lCraft").build(), event -> {
        process = new MachineProcess(wantsToCraft.result, new Timer(4000));
    });

    private void openItem(MachineCraftable item, Player player) {
        gui.setTitle("§b" + item.menuItem.getItemMeta().getDisplayName());
        progress = System.currentTimeMillis() * 100 / process.getTimer().getExpiry();
        PercentageBar percentageBar = new PercentageBar(0, 4, 9, 1);
        percentageBar.setBackgroundItem(new GuiItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true)));
        percentageBar.setFillItem(new GuiItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§7Progress: §a" ).build()));
        percentageBar.setPercentage(progress);
        gui.addPane(percentageBar);
        StaticPane background = new StaticPane(0, 0, 9, 4);
        background.fillWith(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true));
        StaticPane bottomBar = new StaticPane(0, 5, 9, 1);
        gui.show(player);
    }

    private void tryCraft(StaticPane pane, Gui gui) {
        Collection<GuiItem> ingredients = pane.getItems();
        ingredients.remove(confirm);
        for (MachineCraftable craftable : MachineCraftable.values()) {
            if (craftable.type != MachineCraftable.Type.CARPENTRY) continue;

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        
    }

    @Override
    public void tick() {
        if (process.getTimer().getRemaining() <= 0)
            process = null;
        gui.update();
        if (craftPane != null) tryCraft(craftPane, gui);
    }

}
