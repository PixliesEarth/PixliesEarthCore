package eu.pixliesearth.core.custom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a custom block</h3>
 *
 */
public class CustomBlock extends CustomItem {
	
	public CustomBlock() {
		
	}
	/**
	 * Returns the amount of experience to drop on the block being broke
	 * 
	 * @return The experience to drop as an {@link Integer}
	 */
	public int getExperience() {
		return 0;
	}
	/**
	 * Called when the custom block is interacted with
	 * 
	 * @param event The {@link PlayerInteractEvent} that interacted with the block
	 * @return If the event should be cancelled
	 */
	public boolean onBlockIsInteractedWith(PlayerInteractEvent event) {
		return false;
	}
	/**
	 * Called when the custom block is placed
	 * 
	 * @param event The {@link BlockBreakEvent} that occurred
	 * @return If the event should be cancelled
	 */
	public boolean BlockBreakEvent(BlockBreakEvent event) {
		return false;
	}
	/**
	 * Called when the custom block is broken
	 * 
	 * @param event The {@link BlockPlaceEvent} that occurred
	 * @return If the event should be cancelled
	 */
	public boolean BlockPlaceEvent(BlockPlaceEvent event) {
		return false;
	}
	/**
	 * Called every tick
	 * 
	 * @param location The {@link CustomBlock}'s {@link Location}
	 */
	public void onTick(Location location) {
		
	}
	/**
	 * Called when a block explodes and it will break this block
	 * 
	 * @param event The {@link BlockExplodeEvent} that occurred
	 * @return If the custom block should be broken
	 */
	public boolean BlockExplodeEvent(BlockExplodeEvent event) {
		return false;
	}
	/**
	 * Called when an entity explodes and it will break this block
	 * 
	 * @param event The {@link EntityExplodeEvent} that occurred
	 * @return If the custom block should be broken
	 */
	public boolean EntityExplodeEvent(EntityExplodeEvent event) {
		return false;
	}
	// TODO: notes
	public Set<Block> getSurroundingBlocks(Location location) {
		Set<Block> set = new HashSet<Block>();
		World w = location.getWorld();
		int x = location.getBlockX();
    	int y = location.getBlockY();
    	int z = location.getBlockZ();
    	Block b2 = w.getBlockAt(x+1, y, z);
    	Block b3 = w.getBlockAt(x-1, y, z);
    	Block b4 = w.getBlockAt(x, y, z+1);
    	Block b5 = w.getBlockAt(x, y, z-1);
    	Block b6 = w.getBlockAt(x, y+1, z);
    	Block b7 = w.getBlockAt(x, y-1, z);
    	if (b2.getType() != Material.AIR)
    		set.add(b2);
    	if (b3.getType() != Material.AIR)
    		set.add(b3);
    	if (b4.getType() != Material.AIR)
    		set.add(b4);
    	if (b5.getType() != Material.AIR)
    		set.add(b5);
    	if (b6.getType() != Material.AIR)
    		set.add(b6);
    	if (b7.getType() != Material.AIR)
    		set.add(b7);
		return set;
	}
	// TODO: notes
	public Set<Block> getSurroundingCustomBlocks(Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Set<Block> set = new HashSet<Block>();
	   	for (Block b : getSurroundingBlocks(location)) {
	   		if (h.isCustomBlockAtLocation(b.getLocation()))
	   			set.add(b);
	   	}
		return set;
	}
}
