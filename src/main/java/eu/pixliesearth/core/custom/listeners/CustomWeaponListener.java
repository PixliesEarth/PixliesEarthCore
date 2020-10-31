package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomWeapon;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom weapons</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomWeaponListener extends CustomListener {
	
	@EventHandler
	public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return; // Not a player
		if (((Player) event.getDamager()).getInventory().getItemInMainHand()==null || ((Player) event.getDamager()).getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
		if (CustomItemUtil.isItemStackACustomItem(((Player) event.getDamager()).getInventory().getItemInMainHand())) 
			if (CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(((Player) event.getDamager()).getInventory().getItemInMainHand())) instanceof CustomWeapon) 
				event.setCancelled(((CustomWeapon)CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(((Player) event.getDamager()).getInventory().getItemInMainHand()))).EntityDamageEvent(event));
	}
}
