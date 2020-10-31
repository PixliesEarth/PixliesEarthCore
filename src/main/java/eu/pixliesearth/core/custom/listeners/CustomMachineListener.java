package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomMachine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for machines</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomMachineListener extends CustomListener {
	
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent event) {
		String s = event.getView().getTitle();
		if (s==null) return;
		for (CustomMachine m : CustomFeatureLoader.getLoader().getHandler().getCustomMachines())
			if (m.getInventoryTitle().equals(s)) 
				event.setCancelled(m.InventoryClickEvent(event));
	}
	
	@EventHandler
	public void InventoryCloseEvent(InventoryCloseEvent event) {
		String s = event.getView().getTitle();
		if (s==null) return;
		for (CustomMachine m : CustomFeatureLoader.getLoader().getHandler().getCustomMachines())
			if (m.getInventoryTitle().equals(s)) 
				m.InventoryCloseEvent(event);
	}
}
