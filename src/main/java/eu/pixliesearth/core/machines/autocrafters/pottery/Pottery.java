package eu.pixliesearth.core.machines.autocrafters.pottery;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Pottery extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/d6eeab45beec2f0861026b9a354b63f23c2b88d79e41b7504c87bcbeb779e")).setDisplayName("§6§lPottery").build();

    public Pottery(String id, Location location) {
        super(id, location, MachineType.KILN, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
    }

    public String getTitle() {
        return "§6§lPottery";
    }

}
