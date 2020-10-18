package eu.pixliesearth.core.custom;

import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
	//TODO: event when interacted with
}
