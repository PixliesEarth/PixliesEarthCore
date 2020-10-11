package eu.pixliesearth.core.machines.autocrafters.compressor;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.autocrafters.FuelableAutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Compressor extends FuelableAutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/51af4b784f11465996fcd43d088381cd381fd4ce8ae4f29f368a271b98f03822")).setDisplayName("§c§lCompressor").build();

    public Compressor(String id, Location location) {
        super(id, location, MachineType.COMPRESSOR, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
        fuel = 0;
    }

    public Compressor(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType, int fuel) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
        this.fuel = fuel;
    }

    public String getTitle() {
        return "§c§lCompressor";
    }

}
