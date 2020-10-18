package eu.pixliesearth.core.custom.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.utils.CustomItemUtil;
import lombok.SneakyThrows;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Handles events for custom crafting</h3>
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
			Map<Integer, String> cwaftin = new HashMap<Integer, String>();
			for (int i = 0; i<9; i++) {
				if (inv.getItem(i)==null || inv.getItem(i).getType().equals(MinecraftMaterial.AIR.getMaterial()))
					cwaftin.put(i, MinecraftMaterial.AIR.getUUID());
				else
					cwaftin.put(i, CustomItemUtil.getUUIDFromItemStack(inv.getItem(i)));
			}
			craft(event.getPlayer(), cwaftin, inv);
		} else {
			// open inv
		}
	}
	
	public boolean craft(Player player, Map<Integer, String> map, Inventory inv) {
		CustomRecipe customRecipe = getCustomRecipe(map);
		if (customRecipe==null) {
			player.sendMessage("§cInvalid recipe");
			return false;
		} else {
			player.sendMessage("§aCrafting "+customRecipe.getResultUUID());
			ItemStack[] isl = inv.getContents();
			for (int i = 0; i < isl.length; i++) {
				ItemStack is = isl[i];
				if (is==null || is.getType().equals(MinecraftMaterial.AIR.getMaterial())) continue;
				is.setAmount(is.getAmount()-1);
				isl[i] = is;
			}
			inv.setContents(isl);
			for (int i = 0; i < customRecipe.getResultAmount(); i++)
				player.getLocation().getWorld().dropItemNaturally(player.getLocation(), CustomItemUtil.getItemStackFromUUID(customRecipe.getResultUUID()));
			return true;
		}
	}
	
	public CustomRecipe getCustomRecipe(Map<Integer, String> map) {
		for (CustomRecipe customRecipe : CustomFeatureLoader.getLoader().getHandler().getCustomRecipes()) {
			int i = compare(customRecipe.getContentsList(), map, true);
			if (i>=9)
				return customRecipe;
		}
		return null;
	}
	
	public int compare(Map<Integer, String> map, Map<Integer, String> map2, boolean debug) {
		if (debug) System.out.println("[DEBUG] Starting crafting map comarison!");
		int res = 0;
		for (int i = 0; i<9; i++) {
			String s = map.get(i);
			if (debug) System.out.println("[DEBUG] Map["+i+"]  - "+s+";");
			String s2 = map2.get(i);
			if (debug) System.out.println("[DEBUG] Map2["+i+"] - "+s2+";");
			if (s.equalsIgnoreCase(s2)) {
				res += 1;
				if (debug) System.out.println("[DEBUG] Values are the same");
			} else 
				if (debug) System.out.println("[DEBUG] Values are not the same");
		}
		if (debug) System.out.println("[DEBUG] Finished comparison with exit value of "+res);
		return res;
	}
	
}
