package eu.pixliesearth.core.customcrafting;

import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class CustomCrafting implements Listener {

    static final List<Integer> craftingSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);
    static final List<Integer> bottomBarSlots = Arrays.asList(45, 46, 47, 48, 50, 51, 52, 53);

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "§e§lCrafting");
        for (int i = 0; i < inv.getSize(); i++)
            if (!craftingSlots.contains(i) && !bottomBarSlots.contains(i) && i != 24)
                inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
        for (int i : bottomBarSlots)
            inv.setItem(i, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§c§lWRONG RECIPE").build());
        player.openInventory(inv);
    }



}
