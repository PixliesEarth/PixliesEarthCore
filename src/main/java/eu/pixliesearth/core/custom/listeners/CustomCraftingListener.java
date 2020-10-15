package eu.pixliesearth.core.custom.listeners;

import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.NBTUtil;
import lombok.SneakyThrows;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom armours</h3>
 *
 * @apiNote TODO: notes
 */
public class CustomCraftingListener extends CustomListener {
	
	@EventHandler
    @SneakyThrows
    public void PlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getClickedBlock() == null) return;
		CustomBlock cb = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getClickedBlock().getLocation());
		if (cb==null) return;
		if (!cb.getUUID().equalsIgnoreCase("Pixlies:Crafting_Table")) return;
		Dispenser d = (Dispenser) event.getClickedBlock().getState();
		if (d==null) return;
		Inventory inv = d.getInventory();
		if (inv==null) return;
		
		if (event.getPlayer().isSneaking()) {
			//craft
			ItemStack[] isl = inv.getContents();
			for (ItemStack is : isl) {
				String id = NBTUtil.getTagsFromItem(is).getString("UUID");
				if (id==null) 
					id = MinecraftMaterial.getMinecraftMaterialFromItemStack(is);
				
			}
		} else {
			// open inv
		}
	}
}
