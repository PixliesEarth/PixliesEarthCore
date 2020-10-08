package eu.pixliesearth.core.custom.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomWeapon;
import eu.pixliesearth.utils.CustomItemUtil;
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
		for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) {
			if (c instanceof CustomWeapon) 
				if (CustomItemUtil.getUUIDFromItemStack(((Player)event.getDamager()).getInventory().getItemInMainHand()).equals(c.getUUID())) 
					event.setCancelled(((CustomWeapon)c).EntityDamageEvent(event));
		}
	}
}
