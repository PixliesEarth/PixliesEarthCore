package eu.pixliesearth.core.interfaces;

import eu.pixliesearth.core.machines.Machine.MachineType;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author BradBot_1
 * 
 * Just used to make interacting with machines simpler :)
 *
 */
public interface Machine {
	/**
	 * The machines name
	 * 
	 * @return machine name
	 */
	public String getTitle();
	/**
	 * The machines item
	 * 
	 * @return machine item
	 */
	public ItemStack getItem();
	/**
	 * The machines MachineType
	 * 
	 * @return the MachineType
	 */
	public MachineType getType();
}
