package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.NBTUtil;
import lombok.Getter;
import lombok.SneakyThrows;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom inventories</h3>
 * 
 * @apiNote TODO: notes
 */
public class CustomInventoryListener extends CustomListener {
	
	public static @Getter String unclickableItemUUID = "Pixlies:Inventory_UnclickableGlass";
	
	@EventHandler
    @SneakyThrows
    public void InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
		String id = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("UUID");
		if (id==null) return;
		if (id.equals(getUnclickableItemUUID())) event.setCancelled(true);
	}
}
