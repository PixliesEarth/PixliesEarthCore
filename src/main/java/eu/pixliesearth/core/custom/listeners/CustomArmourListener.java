package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import eu.pixliesearth.core.custom.CustomArmour;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.CustomItemUtil;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom armours</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomArmourListener extends CustomListener {
	
	@EventHandler
	public void EntityDamageEvent(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return; // Not a player
		ItemStack[] armours = ((Player)event.getEntity()).getInventory().getArmorContents();
		for (ItemStack is : armours) {
			if (is==null) continue;
			if (CustomItemUtil.isItemStackACustomItem(is)) {
				if (CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is)) instanceof CustomArmour) {
					event.setCancelled(((CustomArmour)CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is))).EntityDamageEvent(event));
				}
			}
		}
	}
	
	@EventHandler
	public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return; // Not a player
		ItemStack[] armours = ((Player)event.getDamager()).getInventory().getArmorContents();
		for (ItemStack is : armours) {
			if (is==null) continue;
			if (CustomItemUtil.isItemStackACustomItem(is)) {
				if (CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is)) instanceof CustomArmour) {
					event.setCancelled(((CustomArmour)CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is))).EntityDamageByEntityEvent(event));
				}
			}
		}
	}
	
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent event) {
		if (event.isCancelled()) return;
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getInventory() instanceof PlayerInventory)) return;
		ItemStack is = event.getCurrentItem();
		if (is==null || is.getType().equals(Material.AIR)) return;
		if (!CustomItemUtil.isItemStackACustomItem(is)) return;
		CustomItem c = CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(is));
		if (c==null) return;
		if (!(c instanceof CustomArmour)) return;
		if (!event.getSlotType().equals(SlotType.ARMOR)) return;
		event.setCancelled(((CustomArmour)c).ArmourEquipEvent(event));
	}
}
