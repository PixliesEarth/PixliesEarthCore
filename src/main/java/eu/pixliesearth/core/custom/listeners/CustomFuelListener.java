package eu.pixliesearth.core.custom.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomFuel;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom fuels</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomFuelListener extends CustomListener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void FurnaceBurnEvent(FurnaceBurnEvent event) {
		ItemStack is = event.getFuel();
		if (is==null || is.getType().equals(MinecraftMaterial.AIR.getMaterial())) return;
		String id = CustomItemUtil.getUUIDFromItemStack(is);
		if (id==null) return;
		CustomItem ci = CustomItemUtil.getCustomItemFromUUID(id);
		if (ci==null) return;
		if (!(ci instanceof CustomFuel)) return;
		event.setBurnTime(Math.round(((CustomFuel)ci).getBurnTime()));
		event.setBurning(true);
		((CustomFuel)ci).onUsedAsFuel(event.getBlock().getLocation());
	}
}
