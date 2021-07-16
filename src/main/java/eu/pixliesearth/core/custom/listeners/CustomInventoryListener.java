package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.CustomItemUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom inventories</h3>
 *
 */
public class CustomInventoryListener extends CustomListener {
	
	public static @Getter String unclickableItemUUID = "Inventory:UnclickableGlass";

}
