package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.NBTUtil;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom items</h3>
 *
 * @apiNote TODO: this is not completed yet! notes
 */
public class CustomItemListener extends CustomListener {
	
	//TODO: add items to the creative menu
	
	@EventHandler
    @SneakyThrows
    public void PlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getItem()==null || event.getItem().getType().equals(Material.AIR)) return;
		if (!event.getHand().equals(EquipmentSlot.HAND)) return;
		String id = NBTUtil.getTagsFromItem(event.getItem()).getString("UUID");
		if (id==null) return;
		CustomItem c = CustomItemUtil.getCustomItemFromUUID(id);
		if (c==null) return;
		event.setCancelled(c.PlayerInteractEvent(event));
	}
	
	@EventHandler
    @SneakyThrows
	public void BlockBreakEvent(BlockBreakEvent event) {
		ItemStack i = event.getPlayer().getInventory().getItemInMainHand();
		if (i==null || i.getType().equals(Material.AIR)) return;
		String id = NBTUtil.getTagsFromItem(i).getString("UUID");
		if (id==null) return;
		CustomItem ci = CustomItemUtil.getCustomItemFromUUID(id);
		if (ci==null) return;
		event.setCancelled(ci.onBlockBrokeWithItem(event));
	}
}
