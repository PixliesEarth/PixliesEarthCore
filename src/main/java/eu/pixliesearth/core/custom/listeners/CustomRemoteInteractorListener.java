package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomRemoteInteractorListener extends CustomListener {
	
	@EventHandler
	public void InventoryCloseEvent(InventoryCloseEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase("§cRemote Interactor")) return;
		event.getPlayer().getInventory().setItemInMainHand(new ItemBuilder(event.getPlayer().getInventory().getItemInMainHand()).addNBTTag("CONTENTS", InventoryUtils.makeInventoryToJSONObject(event.getInventory()).toString(), NBTTagType.STRING).build());
	}
	
	@EventHandler
    @SneakyThrows
    public void InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return;
		if (!event.getView().getTitle().equalsIgnoreCase("§cRemote Interactor")) return;
		String id = CustomItemUtil.getUUIDFromItemStack(event.getCurrentItem());
		if (id==null) {
			event.setCancelled(true);
			return;
		} else {
			String id2 = CustomItemUtil.getUUIDFromItemStack(event.getInventory().getItem(12));
			if (id2==null || id2.equalsIgnoreCase(MinecraftMaterial.AIR.getUUID())) return;
			if (!id2.equalsIgnoreCase("Pixlies:ICBM_Location_Holder")) {
				event.getWhoClicked().getInventory().addItem(event.getInventory().getItem(12));
				event.getInventory().clear(12);
				event.setCancelled(true);
				return;
			}
		}
		if (id.equalsIgnoreCase("Pixlies:Remote_Interactor")) event.setCancelled(true);
	}
	
	public void InventoryMoveItemEvent(InventoryMoveItemEvent event) {
		
	}
}
