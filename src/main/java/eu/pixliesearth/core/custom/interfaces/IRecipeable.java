package eu.pixliesearth.core.custom.interfaces;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomRecipe;
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
public interface IRecipeable {
	
	public Inventory getCraftingExample(CustomRecipe customRecipe);
	
	public static String craftingExampleTitle = "§6Machines : Recipe";
	
	public static int recipeItemSlot = 46;
	public static int cratinInItemSlot = 47;
	
	public static ItemStack backItem = Constants.backItem.clone();
	public static ItemStack closeItem = Constants.closeItem.clone();
	public static ItemStack nextItem = Constants.nextItem.clone();

}
