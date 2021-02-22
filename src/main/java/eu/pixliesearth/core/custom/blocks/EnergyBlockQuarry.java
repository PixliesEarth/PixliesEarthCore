package eu.pixliesearth.core.custom.blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.interfaces.IHopperable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ExplosionCalculator;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockQuarry extends CustomEnergyBlock implements IHopperable {
	
	public EnergyBlockQuarry() {
		
	}
	
	@Override
    public Material getMaterial() {
        return Material.BLACK_GLAZED_TERRACOTTA;
    }
	
	private static final Set<String> UNBREAKABLES = new ExplosionCalculator(new Location(Bukkit.getWorld("world"), 0, 0, 0), 0, true).getUnbreakableBlocks();
	
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
			
	        
	        for (int y = maxY; y >= 0; --y) {
	        	for (int x = minX; x <= maxX; ++x) {
	        		for (int z = minZ; z <= maxZ; ++z) {
	        			Block b = chunk.getBlock(x & 15, y, z & 15);
	                    if (b==null || b.getType().equals(Material.AIR)) continue;
	                    // TODO: ignore unbreakable blocks
	        			String id = CustomItemUtil.getUUIDFromLocation(b.getLocation());
	        			if (id==null) continue;
	        			ItemStack is = CustomItemUtil.getItemStackFromUUID(id);
	        			if (is==null) continue;
	        			if (UNBREAKABLES.contains(id)) return;
	        			inventory.addItem(is);
	        			Bukkit.getScheduler().scheduleSyncDelayedTask(h.getInstance(), new Runnable() {
							@Override
							public void run() {
								// makeParticleLine(location.clone(), b.getLocation().clone());
								h.removeCustomBlockFromLocation(b.getLocation());
								b.setType(Material.AIR);
							}
	        			}, 1L);
	        			h.removePowerFromLocation(location, energyPerOperation);
	        			return;
	        		}
	        	}
	        }
	        
	        /*
	        for (int x = minX; x <= maxX; ++x) {
	            for (int y = 0; y <= maxY; ++y) {
	                for (int z = minZ; z <= maxZ; ++z) {
	                    Block b = chunk.getBlock(x & 15, y, z & 15);
	                    if (b==null || b.getType().equals(Material.AIR)) continue;
	                    // TODO: ignore unbreakable blocks
	        			String id = CustomItemUtil.getUUIDFromLocation(b.getLocation());
	        			if (id==null) continue;
	        			ItemStack is = CustomItemUtil.getItemStackFromUUID(id);
	        			if (is==null) continue;
	        			inventory.addItem(is);
	        			Bukkit.getScheduler().scheduleSyncDelayedTask(h.getInstance(), new Runnable() {
							@Override
							public void run() {
								h.removeCustomBlockFromLocation(b.getLocation());
								b.setType(Material.AIR);
							}
	        			}, 1L);
	        			h.removePowerFromLocation(location, energyPerOperation);
	        			return;
	                }
	            }
	        }
	        */
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
    public String getDefaultDisplayName() {
        return "§6Quarry";
    }

    @Override
    public String getUUID() {
        return "Machine:Quarry"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
	
    @Override
    public boolean InventoryClickEvent(InventoryClickEvent event) {
    	ItemStack is = event.getCurrentItem();
    	if (is==null) return false;
    	String s = CustomItemUtil.getUUIDFromItemStack(is);
    	if (s==null) return false;
    	if (s.equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID())) return true;
    	return false;
    }
    
    @Override
    public boolean BlockExplodeEvent(BlockExplodeEvent event) {
		return true;
	}
	
    @Override
	public boolean EntityExplodeEvent(EntityExplodeEvent event) {
		return true;
	}
    
    public void makeParticleLine(Location from, Location to) {
    	if (!from.getWorld().equals(to.getWorld())) return; // Cant be cross world
    	
        Vector vector = to.clone().toVector().subtract(from.clone().toVector());
        
        for (double i = 1; i <= from.distance(to); i += 0.5) {
            vector.multiply(i);
            from.add(vector);
            // from.getWorld().spawnParticle(Particle.REDSTONE, from, 50, new DustOptions(Color.ORANGE, 1));
            from.getWorld().spawnParticle(Particle.REDSTONE, from.getX(), from.getY(), from.getZ(), 1, new DustOptions(Color.ORANGE, 1));
            from.subtract(vector);
            vector.normalize();
        }
    }

	@Override
	public ItemStack takeFirstTakeableItemFromIHopperableInventory(Location location) {
		List<Integer> ints = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53);
		Inventory inv = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		for (int i = 0; i < inv.getSize(); i++) {
			if (ints.contains(i)) continue;
			ItemStack itemStack = inv.getItem(i);
			if (itemStack==null || itemStack.getType().equals(Material.AIR)) continue;
			ItemStack itemStack2 = itemStack.clone().asOne();
			itemStack.setAmount(itemStack.getAmount()-1);
			return itemStack2;
		}
		return null;
	}

	@Override
	public boolean addItemToIHopperableInventory(Location location, ItemStack itemStack) {
		Inventory inv = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (inv.firstEmpty()==-1) return false;
		inv.addItem(itemStack);
		return true;
	}
}
