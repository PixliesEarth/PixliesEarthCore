package eu.pixliesearth.core.custom;

import org.bukkit.Location;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a custom fuel</h3>
 *
 */
public class CustomFuel extends CustomItem {
	
	public CustomFuel() {
		
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
	 * The time that the fuel will burn for
	 */
	public long getBurnTime() {
		return 5L;
	}
	/**
	 * Called when the item is used as fuel
	 * 
	 * @deprecated currently broken!
	 */
	@Deprecated
	public void onUsedAsFuel(Location location) { }
}
