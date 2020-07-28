package eu.pixliesearth.core.machines;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.PercentageBar;
import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.nations.entities.nation.Era;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CarpentryMill extends Machine {

    public CarpentryMill(Location location) {
        super("Carpentry Mill", location);
        register(this);
    }

    private Gui gui;
    private float progress;

    //TODO
    private void openItem(CustomItem item, Player player) {
        PercentageBar percentageBar = new PercentageBar(0, 4, 9, 1);
        percentageBar.setBackgroundItem(new GuiItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setNoName().build(), event -> event.setCancelled(true)));
        percentageBar.setFillItem(new GuiItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§7Progress: §a" ).build()));
        percentageBar.setPercentage(progress);
        gui.addPane(percentageBar);
        StaticPane bottomBar = new StaticPane(0, 5, 9, 1);
        gui.show(player);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        
    }

    @Override
    public void tick() {
        gui.update();
    }

}
