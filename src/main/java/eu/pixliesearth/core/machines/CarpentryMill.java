package eu.pixliesearth.core.machines;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarpentryMill extends Machine {

    private MachineProcess task;

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
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§b§lCarpentry Mill");
        for (int i = 0; i < inventory.getSize(); i++) {
            if (craftSlots.contains(i) || resultSlots.contains(i)) continue;
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setNoName().build());
        }
        
        player.openInventory(inventory);
    }

    @Override
    public void tick() {

    }

}
