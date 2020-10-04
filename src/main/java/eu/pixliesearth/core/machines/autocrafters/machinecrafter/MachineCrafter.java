package eu.pixliesearth.core.machines.autocrafters.machinecrafter;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class MachineCrafter extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/8245a1c3e8d7c3d59d05e3634b04af4cbf8d11b70e2a40e2e6364386db49e737")).setDisplayName("§b§lMachine Crafter").build();

    public MachineCrafter(String id, Location location) {
        super(id, location, MachineType.MACHINE_CRAFTER, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
    }

    public MachineCrafter(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
    }

    @Override
    public String getTitle() {
        return "§b§lMachine Crafter";
    }

}
