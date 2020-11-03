package eu.pixliesearth.core.custom.interfaces;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomRecipe;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>An interface to give a gui for custom recipes</h3>
 *
 * <p>
 *  You can use the
 * 
 * 
 * </p>
 */
public interface Recipeable {
	
	public Inventory getCraftingExample(CustomRecipe customRecipe);
	
	public static String craftingExampleTitle = "§6Machines : Recipe";
	
	public static int recipeItemSlot = 46;
	
	public static ItemStack backItem = new ItemBuilder(Material.ARROW).setDisplayName("§bBack").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MBACK", NBTTagType.STRING).build(); // Back
	public static ItemStack closeItem = new ItemBuilder(Material.BARRIER).setDisplayName("§cClose").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MCLOSE", NBTTagType.STRING).build(); // Close
	public static ItemStack nextItem = new ItemBuilder(Material.ARROW).setDisplayName("§bNext").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).addNBTTag("EXTRA", "MNEXT", NBTTagType.STRING).build(); // Next
}
