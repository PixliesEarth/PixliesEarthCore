package eu.pixliesearth.core.machines.autocrafters;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.cargo.InputNode;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class FuelableAutoCrafterMachine extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0")).setDisplayName("§c§lFuelableAutoCrafter").build();
    protected int fuel;

    public FuelableAutoCrafterMachine(String id, Location location, MachineType type, ItemStack item, Timer timer, Hologram armorStand, MachineCraftable wantsToCraft) {
        super(id, location, type, item, timer, armorStand, wantsToCraft);
    }

    @Override
    protected void beforeUpdate() {
        if (fuel == 0) {
            int radius = 1;
            final Block block = location.getBlock();
            x:
            for (int x = -(radius); x <= radius; x++) {
                for (int z = -(radius); z <= radius; z++) {
                    final Block relative = block.getRelative(x, 0, z);
                    if (instance.getUtilLists().machines.containsKey(relative.getLocation()) && instance.getUtilLists().machines.get(relative.getLocation()) instanceof InputNode) {
                        InputNode in = (InputNode) instance.getUtilLists().machines.get(relative.getLocation());
                        if (timer == null) {
                            boolean take = in.takeItem(MachineCraftable.CHARCOAL_CHUNK.results.get(0));
                            if (take) {
                                fuel = 100;
                            }
                        }
                        break x;
                    }
                }
            }
        }
    }

    public Map<String, Object> extras() {
        return Collections.singletonMap("fuel", fuel);
    }

}
