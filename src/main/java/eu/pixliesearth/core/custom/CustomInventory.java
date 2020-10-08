package eu.pixliesearth.core.custom;

import org.bukkit.inventory.Inventory;

public class CustomInventory {
	
	//TODO
	
	public CustomInventory() {
		
	}
	
	public int getSize() {
		return 0; // Must be a multiple of nine!
	}
	
	public String getTitle() {
		return null;
	}
	
	public Inventory buildInventory() {
		return null;
	}
	
	/**
	 * This is not a per gui id!
	 * 
	 * @apiNote this id is used for telling different custom guis apart!
	 * 
	 * @return The gui id
	 */
	public String getUUID() {
		return null;
	}
	
}
