package eu.pixliesearth.utils;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Does basic things for CustomItems</h3>
 *
 *
 */
public class CustomItemUtil {
	
	public static String getUUIDFromItemStack(ItemStack itemStack) {
		if (itemStack==null || itemStack.getType().equals(Material.AIR)) 
			return MinecraftMaterial.AIR.getUUID();
		String s = NBTUtil.getTagsFromItem(itemStack).getString("UUID");
		if (s==null || s.equals("")) 
			return MinecraftMaterial.getMinecraftMaterialFromItemStack(itemStack);
		return s;
	}
	
	public static boolean isItemStackACustomItem(ItemStack itemStack) {
		return getUUIDFromItemStack(itemStack)!=null;
	}
	
	public static ItemStack getCustomItemFromUUID(String id) {
		return new ItemStack(MinecraftMaterial.getMinecraftMaterialFromUUID(id).getMaterial());
	}
	
	public static ItemStack getItemStackFromUUID(String id) {
		Material m = MinecraftMaterial.getMinecraftMaterialFromUUID(id).getMaterial();
		if (m==null || m.equals(Material.AIR)) {
			return null;
		} else {
			return new ItemStack(m);
		}
	}
	
	public static String getUUIDFromLocation(Location l) {
		return MinecraftMaterial.getMinecraftMaterialFromMaterial(l.getBlock().getType()).getUUID();
	}
}