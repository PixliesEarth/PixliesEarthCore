package eu.pixliesearth.core.machines.cargo;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class OutputNode extends CargoMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/7e80cebc2eb0a13496b274a908158d6105bdaf1a51d82e5d2417bb55dd20f4da")).setDisplayName("§c§lOutput Node").build();

    public OutputNode(String id, Location location) {
        super(id, location, MachineType.OUTPUT_NODE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
    }

    public OutputNode(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, Inventory storage) {
        super(id, location, MachineType.OUTPUT_NODE, item, timer, armorStand, wantsToCraft);
        this.storage = storage;
    }

    @Override
    public String getTitle() {
        return "§c§lOutput Node";
    }

    @Override
    public void tick() {
        try {
            int radius = 1;
            final Block block = location.getBlock();
            x:
            for (int x = -(radius); x <= radius; x++) {
                for (int z = -(radius); z <= radius; z++) {
                    final Block relative = block.getRelative(x, 0, z);
                    if (relative.getState() instanceof Container) {
                        Container container = (Container) relative.getState();
                        for (ItemStack item : storage.getContents()) {
                            if (item == null) continue;
                            if (container.getInventory().firstEmpty() == -1) break;
                            container.getInventory().addItem(item);
                            Methods.removeRequiredAmount(item, storage);
                            break x;
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }

}
