package eu.pixliesearth.core.custom;

import eu.pixliesearth.nations.entities.nation.Era;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author BradBot_1
 * 
 * <h3></h3>
 * 
 * @apiNote TODO: notes
 *
 * @deprecated Use {@link CustomRecipe} instead!
 */
@Deprecated
public class CustomMachineRecipe {
	
	public CustomMachineRecipe() {
		
	}
	
	public String getMachineUUID() {
		return null;
	}; // Machine to be crafted in
	
	public ItemStack getIcon() {
		return null;
	}; // Icon for selecting recipes
	
	public Set<ItemStack> getIngredients() {
		return new HashSet<ItemStack>();
	}; // The ingredients to make the results
	
	public Set<ItemStack> getResults() {
		return new HashSet<ItemStack>();
	}; // The results
	
	public int getSeconds() {
		return 0;
	}; // How long the recipe takes
	
	public Era eraNeeded() {
		return Era.ANCIENT;
	}; // What era is needed to craft the results
	
	public String getUUID() {
		return null;
	}
	
}
