package eu.pixliesearth.utils;

import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomItem;
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
		return NBTUtil.getTagsFromItem(itemStack).getString("UUID");
	}
	
	public static boolean isItemStackACustomItem(ItemStack itemStack) {
		return getUUIDFromItemStack(itemStack)!=null;
	}
	
	public static CustomItem getCustomItemFromUUID(String id) {
		return CustomFeatureLoader.getLoader().getHandler().getCustomItemFromUUID(id);
	}
	
	public static ItemStack getItemStackFromUUID(String id) {
		return CustomFeatureLoader.getLoader().getHandler().getItemStackFromUUID(id);
	}
}