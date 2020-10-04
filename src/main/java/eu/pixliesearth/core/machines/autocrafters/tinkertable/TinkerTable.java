package eu.pixliesearth.core.machines.autocrafters.tinkertable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import eu.pixliesearth.core.machines.autocrafters.AutoCrafterMachine;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.SkullCreator;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class TinkerTable extends AutoCrafterMachine {

    public static final ItemStack item = new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/83d47199d034fae71e5c7ef1e12bf9f1adbb88c22ad4b0e9453abf8cee5c350b")).setDisplayName("§b§lTinker Table").build();

    public TinkerTable(String id, Location location) {
        super(id, location, MachineType.TINKER_TABLE, item, null, HologramsAPI.createHologram(instance, holoLocation(location)), null);
        armorStand.appendTextLine(getTitle());
    }

    public TinkerTable(String id, Location location, Hologram armorStand, Timer timer, MachineCraftable wantsToCraft, MachineType machineType) {
        super(id, location, machineType, machineType.getItem(), timer, armorStand, wantsToCraft);
    }

    @Override
    public String getTitle() {
        return "§b§lTinker Table";
    }

}
