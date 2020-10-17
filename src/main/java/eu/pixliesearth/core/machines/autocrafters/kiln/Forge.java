package eu.pixliesearth.core.machines.autocrafters.kiln;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.autocrafters.FuelableAutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Forge extends FuelableAutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/368ab8cf594da55f90f593bdc0b41e05925d75daa3ee7f1b49c2421d2bdead0")).setDisplayName("§c§lForge").build();

    public Forge(String id, Location location) {
        super(id, location, MachineType.FORGE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
        fuel = 0;
    }

    public Forge(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType, int fuel) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
        this.fuel = fuel;
    }

    public String getTitle() {
        return "§c§lForge";
    }

}
