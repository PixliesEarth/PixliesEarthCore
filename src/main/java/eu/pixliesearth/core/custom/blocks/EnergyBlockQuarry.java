package eu.pixliesearth.core.custom.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockQuarry extends CustomEnergyBlock {
	
	public EnergyBlockQuarry() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.BLACK_GLAZED_TERRACOTTA;
    }
	
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		int[] ints = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
		for (int i : ints)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(52);
		return inv;
	}
	
	public double getCapacity() {
		return 1500000D;
	}
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		double energyPerOperation = 1000D;
		inventory.setItem(52, buildInfoItem(location));
		if (timer==null) {
			
			h.registerTimer(location, new Timer(1000L));
			
			if (inventory.firstEmpty()==-1) return;
			if (getContainedPower(location)<energyPerOperation) return;
			
			Chunk chunk = location.getChunk();
			
			final int minX = chunk.getX() << 4;
	        final int minZ = chunk.getZ() << 4;
	        final int maxX = minX | 15;
	        final int maxY = location.getBlockY()-1;
	        final int maxZ = minZ | 15;
			
	        for (int x = minX; x <= maxX; ++x) {
	            for (int y = 0; y <= maxY; ++y) {
	                for (int z = minZ; z <= maxZ; ++z) {
	                    Block b = chunk.getBlock(x, y, z);
	                    if (b==null || b.getType().equals(Material.AIR)) continue;
	                    // TODO: ignore unbreakable blocks
	        			String id = CustomItemUtil.getUUIDFromLocation(b.getLocation());
	        			if (id==null) continue;
	        			ItemStack is = CustomItemUtil.getItemStackFromUUID(id);
	        			if (is==null) continue;
	        			inventory.addItem(is);
	        			h.removeCustomBlockFromLocation(b.getLocation());
	        			b.setType(Material.AIR);
	        			h.removePowerFromLocation(location, energyPerOperation);
	        			return;
	                }
	            }
	        }
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(location);
			} else {
				// Do nothing
				return;
			}
		}
	}
	
	@Override
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(event.getBlock().getLocation(), 0D); // Register that it has energy
		event.getPlayer().sendMessage("This is not complete and you may experiance issues!");
		return false;
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Quarry";
    }

    @Override
    public String getUUID() {
        return "Machine:Quarry"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
	
}
