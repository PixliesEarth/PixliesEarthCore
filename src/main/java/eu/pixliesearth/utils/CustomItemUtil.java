package eu.pixliesearth.utils;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>Does basic things for CustomItems</h3>
 *
 * @apiNote TODO: notes
 *
 */
public class CustomItemUtil {
	
	public static String getUUIDFromItemStack(ItemStack itemStack) {
		String s = NBTUtil.getTagsFromItem(itemStack).getString("UUID");
		if (s==null || s.equals("")) 
			return MinecraftMaterial.getMinecraftMaterialFromItemStack(itemStack);
		return s;
	}
	
	public static boolean isItemStackACustomItem(ItemStack itemStack) {
		return getUUIDFromItemStack(itemStack)!=null;
	}
	
	public static CustomItem getCustomItemFromUUID(String id) {
		return CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(id);
	}
	
	public static ItemStack getItemStackFromUUID(String id) {
		ItemStack i = CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(id);
		if (i==null) {
			Material m = MinecraftMaterial.getMinecraftMaterialFromUUID(id).getMaterial();
			if (m==null || m.equals(Material.AIR)) {
				return null;
			} else {
				return new ItemStack(m);
			}
		} else 
			return CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(id);
	}
}