package eu.pixliesearth.core.custom.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomArmour;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
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
		ItemStack hand = ((Player)event.getEntity()).getInventory().getItemInMainHand();
		if (hand==null || hand.getType().equals(Material.AIR)) return;
		for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) {
			if (c instanceof CustomArmour) 
				if (isPlayerWearingCustomArmour(c.getUUID(), (Player)event.getEntity())) 
					event.setCancelled(((CustomArmour)c).EntityDamageEvent(event));
		}
	}
	
	public boolean isPlayerWearingCustomArmour(String id, Player p) {
		ItemStack[] armours = p.getInventory().getArmorContents();
		for (ItemStack i : armours) {
			if (i == null || i.getType() == Material.AIR) continue;
			if (!CustomItemUtil.isItemStackACustomItem(i)) 
				continue;
			if (CustomItemUtil.getUUIDFromItemStack(i).equals(id)) 
				return true;
		}
		return false;
	}
}
