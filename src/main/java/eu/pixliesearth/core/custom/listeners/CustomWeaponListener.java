package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
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
	public void EntityDamageEvent(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return; // Not a player
		((Player) event.getDamager()).getInventory().getItemInMainHand();
		if (((Player) event.getDamager()).getInventory().getItemInMainHand().getType() == Material.AIR) return;
		for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) {
			if (c instanceof CustomWeapon) 
				if (CustomItemUtil.getUUIDFromItemStack(((Player)event.getDamager()).getInventory().getItemInMainHand()).equals(c.getUUID())) 
					event.setCancelled(((CustomWeapon)c).EntityDamageEvent(event));
		}
	}
}
