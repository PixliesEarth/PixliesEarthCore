package eu.pixliesearth.core.custom.interfaces;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface Constants {
	
	public static final String serverVersion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	public static final String craftServerVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

	public static ItemStack backgroundItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
		.setCustomModelData(3)
		.setNoName()
		.addNBTTag("UUID", "Inventory:UnclickableGlass", NBTTagType.STRING)
		.build();

	public static ItemStack backItem = new ItemBuilder(Material.ARROW)
			.setDisplayName("§bBack")
			.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
			.addNBTTag("EXTRA", "MBACK", NBTTagType.STRING)
			.build(); // Back
	public static ItemStack closeItem = new ItemBuilder(Material.BARRIER)
			.setDisplayName("§cClose")
			.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
			.addNBTTag("EXTRA", "MCLOSE", NBTTagType.STRING)
			.build(); // Close
	public static ItemStack nextItem = new ItemBuilder(Material.ARROW)
			.setDisplayName("§bNext")
			.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
			.addNBTTag("EXTRA", "MNEXT", NBTTagType.STRING)
			.build(); // Next


	public static ItemStack backButtonMick = new ItemBuilder(Material.HEART_OF_THE_SEA)
			.setCustomModelData(3)
			.setDisplayName("§bBack")
			.build();

	public static ItemStack nextButtonMick = new ItemBuilder(Material.HEART_OF_THE_SEA)
			.setCustomModelData(2)
			.setDisplayName("§bNext")
			.build();

	public static boolean isNextItem(ItemStack itemStack) {
		if (!hasExtraData(itemStack)) return false;
		return getExtraData(itemStack).equalsIgnoreCase("MNEXT");
	}
	
	public static boolean isBackItem(ItemStack itemStack) {
		if (!hasExtraData(itemStack)) return false;
		return getExtraData(itemStack).equalsIgnoreCase("MBACK");
	}
	
	public static boolean isCloseItem(ItemStack itemStack) {
		if (!hasExtraData(itemStack)) return false;
		return getExtraData(itemStack).equalsIgnoreCase("MClose");
	}
	
	public static boolean hasExtraData(ItemStack itemStack) {
		if (itemStack==null || itemStack.getType().equals(Material.AIR)) return false;
		String s = getExtraData(itemStack);
		if (s==null || s.equalsIgnoreCase("")) return false;
		return true;
	}
	
	public static String getExtraData(ItemStack itemStack) {
		if (itemStack==null || itemStack.getType().equals(Material.AIR)) return "";
		return NBTUtil.getTagsFromItem(itemStack).getString("EXTRA");
	}
	
	public static int getGUIDataSlot = 46;
	
}
