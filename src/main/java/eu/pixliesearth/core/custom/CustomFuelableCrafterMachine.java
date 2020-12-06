package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.interfaces.Constants;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a machine</h3>
 *
 */
public abstract class CustomFuelableCrafterMachine extends CustomCrafterMachine {
	/**
	 * Initialises the class
	 */
	public CustomFuelableCrafterMachine() {
		
	}
	/**
	 * The fuel to use
	 */
	public abstract String getFuelUUID();
	/**
	 * Opens the custom inventory
	 * 
	 * @param player The {@link Player} who wants to open the {@link Inventory}
	 * @param location The {@link Location} of the {@link CustomMachine} that houses the {@link Inventory}
	 */
	@Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (i==null) {
			player.sendMessage("This machine has no inventory!");
			return;
		} else {
			ItemStack is = i.getItem(Constants.getGUIDataSlot);
			ItemStack fuel = i.getItem(52);
			if (is==null || is.getType().equals(Material.AIR)) {
				player.openInventory(i);
			} else {
				if (is.getType().equals(Material.BARRIER)) {
					Inventory inv = getInventory();
					inv.setItem(52, fuel);
					player.openInventory(inv);
					CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
				} else if(Constants.getExtraData(is).equalsIgnoreCase("RECIPE")) {
					String id = CustomItemUtil.getUUIDFromItemStack(is);
					CustomRecipe r = getRecipesOfUUIDInOrderedList(id).get(0);
					Inventory inv = getInventory2(r, 0);
					inv.setItem(52, fuel);
					player.openInventory(inv);
					CustomFeatureLoader.getLoader().getHandler().setInventoryFromLocation(location, inv);
				} else {
					player.openInventory(i);
				}
			}
		}
	}
	/**
	 * @return The {@link CustomFuelableCrafterMachine}'s {@link Inventory}
	 */
	@Override
	public Inventory getInventory() { 
		Inventory inv = super.getInventory();
		inv.clear(52);
		return inv;
	}
	// TODO: notes
	@Override
	public Inventory getInventory2(CustomRecipe r, int i) { 
		Inventory inv = super.getInventory2(r, i);
		inv.clear(52);
		return inv;
	}
	/**
	 * Called to check if the item can be crafted
	 * 
	 * @param location The {@link CustomCrafterMachine} {@link Location}
	 * @param customRecipe The current {@link CustomRecipe}
	 * @return If the crafting can go ahead
	 */
	@Override
	public boolean hasCost(Location location, CustomRecipe customRecipe) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Inventory i = h.getInventoryFromLocation(location);
		ItemStack is = i.getItem(52);
		return (is==null || is.getType().equals(Material.AIR)) ? false : (CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(getFuelUUID())) ? (is.getAmount()>0) ? true: false : false;
	}
	/**
	 * Called to take the cost of the crafting
	 * 
	 * @param location The {@link CustomCrafterMachine} {@link Location}
	 * @param customRecipe The current {@link CustomRecipe}
	 */
	@Override
	public void takeCost(Location location, CustomRecipe customRecipe) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Inventory i = h.getInventoryFromLocation(location);
		i.getItem(52).setAmount(i.getItem(52).getAmount()-1);
	}
}
