package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
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
		int i = event.getPlayer().getInventory().getHeldItemSlot();
		event.getPlayer().getInventory().clear(i);
		Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() {

			@Override
			public void run() {
				event.getPlayer().getInventory().setHeldItemSlot(i);
				event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(event.getPlayer().getInventory().getItemInMainHand()).addNBTTag("CONTENTS", InventoryUtils.makeInventoryToJSONObject(event.getInventory()).toString(), NBTTagType.STRING).build());
			}
			
		}, 1l);
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
	
	@EventHandler
	public void InventoryDragEvent(InventoryDragEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase("§6Backpack")) return;
		event.getNewItems().forEach((ingore,itemStack) -> {
			String uuid = CustomItemUtil.getUUIDFromItemStack(itemStack);
			if (uuid==null || !uuid.equals("Pixlies:Backpack")) return;
			event.setCancelled(true);
		});
	}
}
