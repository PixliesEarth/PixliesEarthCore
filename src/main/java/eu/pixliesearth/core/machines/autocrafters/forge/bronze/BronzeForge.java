package eu.pixliesearth.core.machines.autocrafters.forge.bronze;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.interfaces.Machine;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class BronzeForge extends AutoCrafterMachine implements Machine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/7d4c957342e864def5c6e8f16526fbba296e00aba4b99d43f3d30f510561fbf0")).setDisplayName("§b§lBronzeForge").build();

    public BronzeForge(String id, Location location) {
        super(id, location, MachineType.BRONZE_FORGE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
    }

    public BronzeForge(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
    }

    @Override
    public String getTitle() {
        return "§b§lBronzeForge";
    }
    
    public ItemStack getItem() {
    	return item;
    }
    
    public MachineType getType() {
    	return type;
    }
}
