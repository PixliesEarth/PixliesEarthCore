package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockHeatGenerator extends CustomEnergyBlock {
	
	public EnergyBlockHeatGenerator() {
		
	}
	
	public double getCapacity() {
		return 100D;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (timer==null) {
			h.registerTimer(loc, new Timer(1000L));
			return;
		} else {
			if (timer.hasExpired()) {
				Block b = loc.getBlock();
				World w = loc.getWorld();
				double amountToGive = 0;
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();
				Block b2 = w.getBlockAt(x+1, y, z);
				Block b3 = w.getBlockAt(x-1, y, z);
				Block b4 = w.getBlockAt(x, y, z+1);
				Block b5 = w.getBlockAt(x, y, z-1);
				Block b6 = w.getBlockAt(x, y+1, z);
				Block b7 = w.getBlockAt(x, y-1, z);
				if (b2!=null && b2.getType().equals(MinecraftMaterial.LAVA.getMaterial())) 
					amountToGive += 0.1;
				if (b3!=null && b3.getType().equals(MinecraftMaterial.LAVA.getMaterial())) 
					amountToGive += 0.1;
				if (b4!=null && b4.getType().equals(MinecraftMaterial.LAVA.getMaterial())) 
					amountToGive += 0.1;
				if (b5!=null && b5.getType().equals(MinecraftMaterial.LAVA.getMaterial())) 
					amountToGive += 0.1;
				if (b6!=null && b6.getType().equals(MinecraftMaterial.LAVA.getMaterial())) 
					amountToGive += 0.1;
				if (b7!=null && b7.getType().equals(MinecraftMaterial.LAVA.getMaterial())) 
					amountToGive += 0.1;
				if (loc.getWorld().getEnvironment().equals(Environment.NETHER))  
					amountToGive *= 2;
				if (amountToGive+getContainedPower(loc)>=getCapacity()) return;
				h.addPowerToLocation(loc, amountToGive);
				h.unregisterTimer(loc);
				return;
			} else {
				// Do nothing
				return;
			}
		}
	}
	
	@Override
	public Material getMaterial() {
		return Material.REDSTONE_BLOCK;
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Heat Generator";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Heat_Generator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
