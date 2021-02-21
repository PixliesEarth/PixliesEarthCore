package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import lombok.SneakyThrows;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom backpacks</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomBackpackListener extends CustomListener {
	
	@EventHandler
	public void InventoryCloseEvent(InventoryCloseEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase("§6Backpack")) return;
		event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(event.getPlayer().getInventory().getItemInMainHand()).addNBTTag("CONTENTS", InventoryUtils.makeInventoryToJSONObject(event.getInventory()).toString(), NBTTagType.STRING).build());
	}
	
	@EventHandler
    @SneakyThrows
    public void InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
		if (!event.getView().getTitle().equalsIgnoreCase("§6Backpack")) return;
		String id = CustomItemUtil.getUUIDFromItemStack(event.getCurrentItem());
		if (id==null) return;
		if (id.equalsIgnoreCase("Pixlies:Backpack")) event.setCancelled(true);
	}
	
	@EventHandler public void InventoryDragEvent(InventoryDragEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase("§6Backpack")) return;
		for (ItemStack itemStack : event.getNewItems().values()) {
			if (itemStack==null || itemStack.getType().equals(Material.AIR)) continue;
			if (CustomItemUtil.getUUIDFromItemStack(itemStack).equals("Pixlies:Backpack")) {
				event.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (!event.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase("§6Backpack")) return;
		event.setCancelled(true);
	}
	
}
