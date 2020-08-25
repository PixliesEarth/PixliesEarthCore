package eu.pixliesearth.core.machines.autocrafters.kiln;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.Machine;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
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

public class Kiln extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0")).setDisplayName("§c§lKiln").build();
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

}
