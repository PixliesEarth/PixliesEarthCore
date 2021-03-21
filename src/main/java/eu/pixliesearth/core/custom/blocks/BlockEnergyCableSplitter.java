package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomEnergyCable;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.interfaces.Energyable;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class BlockEnergyCableSplitter extends CustomEnergyCable {

    @Override
    public Material getMaterial() {
        return Material.TARGET;
    }

    @Override
    public double getCapacity() {
        return 1000000000D;
    }

    @Override
    public double getTransferRate() {
        return 0;
    }

    @Override
    public void onTick(Location location, Inventory inventory, Timer timer) {
        CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
        Set<Block> bs = getSurroundingBlocks(location);
        if (bs.isEmpty()) return;
        for (Block b : bs) {
            CustomBlock c = h.getCustomBlockFromLocation(b.getLocation());
            if (c == null) continue;
            if (!(c instanceof Energyable)) continue;
            if (isFull(b.getLocation())) continue;
            Double d = getCapacity(b.getLocation());
            if (d == null) continue;
            if (c instanceof CustomEnergyCable) {
                giveEnergy(location, b.getLocation(), bs.size());
            }
        }
        if (timer == null) {
            h.registerTimer(location, new Timer(15L));
        } else {
            if (timer.hasExpired()) h.unregisterTimer(location);
        }
    }

    @Override
    public String getDefaultDisplayName() {
        return "§dCable Splitter";
    }

    @Override
    public String getUUID() {
        return "Machine:Cable_Splitter"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    public boolean giveEnergy(Location cable, Location to, int blocks) {
        double d = getContainedPower(cable) / blocks;
        return giveEnergy(cable, to, d);
    }

}
