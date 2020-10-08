package eu.pixliesearth.core.custom;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>The base of a custom recipe!</h3>
 *
 * @apiNote TODO: notes, revamp with custom item id support
 */
public class CustomRecipe {
	
	public CustomRecipe() {
		
	}
	
	// For all recipes
	protected @Getter CustomRecipeType type;
	protected @Getter ItemStack result;
	// Crafting
	protected @Getter ItemStack slot1;
	protected @Getter ItemStack slot2;
	protected @Getter ItemStack slot3;
	protected @Getter ItemStack slot4;
	// 9x9
	protected @Getter ItemStack slot5;
	protected @Getter ItemStack slot6;
	protected @Getter ItemStack slot7;
	protected @Getter ItemStack slot8;
	protected @Getter ItemStack slot9;
	// Furnace, BlastFurnace etc
	protected @Getter Material input;
	protected @Getter String recipeName;
	protected @Getter float experience;
	protected @Getter int cookingtime;
	/**
	 * Uses the {@link JavaPlugin} provided to create a {@link NamespacedKey}
	 * 
	 * @param javaplugin The {@link JavaPlugin} that the {@link NamespacedKey} is made under
	 * @return
	 */
	public NamespacedKey getNamespacedKey(JavaPlugin instance) {
		return new NamespacedKey(instance, getRecipeName());
	}
	/**
	 * Creates and returns a recipe based on this classes contents
	 * 
	 * @param javaplugin The {@link JavaPlugin} that the recipe is made under
	 * @return The recipe
	 */
	public Recipe getRecipeAsMinecraftRecipe(JavaPlugin instance) {
		Recipe r;
		switch (getType()) {
		case CraftingTable9x9Shaped :
			ShapedRecipe r1 = new ShapedRecipe(getNamespacedKey(instance), getResult());
			r1.shape("abc",
					"def",
					"ghi");
	    	r1.setIngredient('a', getSlot1());
	    	r1.setIngredient('b', getSlot2());
	    	r1.setIngredient('c', getSlot3());
	    	r1.setIngredient('d', getSlot4());
	    	r1.setIngredient('e', getSlot5());
	    	r1.setIngredient('f', getSlot6());
	    	r1.setIngredient('g', getSlot7());
	    	r1.setIngredient('h', getSlot8());
	    	r1.setIngredient('i', getSlot9());
	    	r = r1;
			break;
		case CraftingTable9x9Shapeless :
			ShapelessRecipe r2 = new ShapelessRecipe(new NamespacedKey(instance, getRecipeName()), getResult());
			r2.addIngredient(getSlot1());
			r2.addIngredient(getSlot2());
			r2.addIngredient(getSlot3());
			r2.addIngredient(getSlot4());
			r2.addIngredient(getSlot5());
			r2.addIngredient(getSlot6());
			r2.addIngredient(getSlot7());
			r2.addIngredient(getSlot8());
			r2.addIngredient(getSlot9());
			r = r2;
			break;
		case CraftingTable2x2Shaped :
			ShapedRecipe r3 = new ShapedRecipe(new NamespacedKey(instance, getRecipeName()), getResult());
			r3.shape("ab ",
					"cd ");
			r3.setIngredient('a', getSlot1());
			r3.setIngredient('b', getSlot2());
			r3.setIngredient('c', getSlot3());
			r3.setIngredient('d', getSlot4());
	    	r = r3;
	    	break;
		case CraftingTable2x2Shapeless :
			ShapelessRecipe r4 = new ShapelessRecipe(new NamespacedKey(instance, getRecipeName()), getResult());
			r4.addIngredient(getSlot1());
			r4.addIngredient(getSlot2());
			r4.addIngredient(getSlot3());
			r4.addIngredient(getSlot4());
			r = r4;
			break;
		case Furnace :
			r = new FurnaceRecipe(new NamespacedKey(instance, getRecipeName()), getResult(), getInput(), getExperience(), getCookingtime());
			break;
		case BlastFurnace :
			r = new BlastingRecipe(new NamespacedKey(instance, getRecipeName()), getResult(), getInput(), getExperience(), getCookingtime());
			break;
		case Smoker :
			r = new SmokingRecipe(new NamespacedKey(instance, getRecipeName()), getResult(), getInput(), getExperience(), getCookingtime());
			break;
		default :
			r = null;
		}
		return r;
	}
	/**
	 * 
	 * @author BradBot_1
	 *
	 * <h3>An enum that stores all supported recipe types</h3>
	 */
	public static enum CustomRecipeType {
		CraftingTable9x9Shaped,
		CraftingTable9x9Shapeless,
		CraftingTable2x2Shaped,
		CraftingTable2x2Shapeless,
		Furnace,
		BlastFurnace,
		Smoker,
		;
	}
}
