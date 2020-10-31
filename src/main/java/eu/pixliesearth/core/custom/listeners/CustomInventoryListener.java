package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.CustomItemUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom inventories</h3>
 * 
 * @apiNote TODO: notes
 */
public class CustomInventoryListener extends CustomListener {
	
	public static @Getter String unclickableItemUUID = "Inventory:UnclickableGlass";
	
	@EventHandler
    @SneakyThrows
    public void InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
		String id = CustomItemUtil.getUUIDFromItemStack(event.getCurrentItem());
		if (id==null) return;
		if (id.equalsIgnoreCase(getUnclickableItemUUID())) event.setCancelled(true);
	}
}
