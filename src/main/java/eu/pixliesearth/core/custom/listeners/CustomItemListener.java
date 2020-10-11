package eu.pixliesearth.core.custom.listeners;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.utils.NBTUtil;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
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
		String id = NBTUtil.getTagsFromItem(event.getItem()).getString("UUID");
		if (id==null) return;
		for (CustomItem c : CustomFeatureLoader.getLoader().getHandler().getCustomItems()) 
			if (c.getUUID().equals(id)) 
				event.setCancelled(c.PlayerInteractEvent(event));
	}
}
