package eu.pixliesearth.core.machines.cargo;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InputNode extends CargoMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/31885b918104e6aa62b6ec9b8e82028dd9da7d2acdfe9c2155edd392285b57e1")).setDisplayName("§b§lInput Node").build();

    public InputNode(String id, Location location) {
        super(id, location, MachineType.INPUT_NODE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
    }

    public InputNode(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, Inventory storage) {
        super(id, location, MachineType.INPUT_NODE, item, timer, armorStand, wantsToCraft);
        this.storage = storage;
    }

    @Override
    public String getTitle() {
        return "§b§lInput Node";
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
                        for (ItemStack item : container.getInventory().getContents()) {
                            if (item == null) continue;
                            if (storage.firstEmpty() == -1) break;
                            storage.addItem(item);
                            Methods.removeRequiredAmount(item, container.getInventory());
                            break x;
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }

}
