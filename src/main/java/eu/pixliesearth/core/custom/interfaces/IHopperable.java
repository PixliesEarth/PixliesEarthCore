package eu.pixliesearth.core.custom.interfaces;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface IHopperable {

	public ItemStack takeFirstTakeableItemFromIHopperableInventory(Location location);

	public boolean addItemToIHopperableInventory(Location location, ItemStack itemStack);

}
