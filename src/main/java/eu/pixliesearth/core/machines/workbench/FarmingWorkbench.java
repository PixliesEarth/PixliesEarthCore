package eu.pixliesearth.core.machines.workbench;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class FarmingWorkbench extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/fdfde9015d7b16fcdadf8090e7443bdb9a7f6748860b5ae2718b44ab0628f4d7")).setDisplayName("§6§lFarming Workbench").build();

    public FarmingWorkbench(String id, Location location) {
        super(id, location, MachineType.FARMING_WORKBENCH, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
    }

    public FarmingWorkbench(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
    }

    @Override
    public String getTitle() {
        return "§6§lFarming Workbench";
    }

    public ItemStack getItem() {
        return item;
    }

    public MachineType getType() {
        return type;
    }

}
