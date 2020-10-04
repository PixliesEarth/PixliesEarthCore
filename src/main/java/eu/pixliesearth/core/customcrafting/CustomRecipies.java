package eu.pixliesearth.core.customcrafting;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.customitems.CustomItems;
import eu.pixliesearth.core.machines.Machine;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import static org.bukkit.Material.*;

public class CustomRecipies {
	/**
	 * Registers some custom recipes
	 */
	public static void register() {
		new CustomRecipe("Machine_Crafter", // Name
				REDSTONE, STONE, REDSTONE, // Row one
				STONE, REDSTONE_BLOCK, STONE, // Row two
				REDSTONE, STONE, REDSTONE, // Row three
				Machine.MachineType.MACHINE_CRAFTER.getItem()); // Result
		new CustomRecipe("Explosive_Pickaxe", TNT, TNT, TNT, AIR, DIAMOND_PICKAXE, AIR, AIR, STICK, AIR, CustomItems.EXPLOSIVE_PICKAXE.getItem());
		new CustomRecipe("Explosive_Shovel", AIR, TNT, AIR, AIR, DIAMOND_SHOVEL, AIR, AIR, STICK, AIR, CustomItems.EXPLOSIVE_SHOVEL.getItem());
		new CustomRecipe("Wooden_Boots", new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(AIR), CustomItems.WOODEN_BOOTS.getItem());
		new CustomRecipe("Wooden_Chestplate", new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), CustomItems.WOODEN_CHESTPLATE.getItem());
		new CustomRecipe("Wooden_Helmet", new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(AIR), CustomItems.WOODEN_HELMET.getItem());
		new CustomRecipe("Wooden_Leggings", new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(AIR), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), new RecipeChoice.MaterialChoice(OAK_LOG, BIRCH_LOG, SPRUCE_LOG, DARK_OAK_LOG, ACACIA_LOG, JUNGLE_LOG), CustomItems.WOODEN_LEGGINGS.getItem());
		new CustomRecipe("Rock_Helmet", STONE, STONE, STONE, STONE, AIR, STONE, AIR, AIR, AIR, CustomItems.ROCK_HELMET.getItem());
		new CustomRecipe("Rock_Chestplate", STONE, AIR, STONE, STONE, STONE, STONE, STONE, STONE, STONE, CustomItems.ROCK_CHESTPLATE.getItem());
		new CustomRecipe("Rock_Leggings", STONE, STONE, STONE, STONE, AIR, STONE, STONE, AIR, STONE, CustomItems.ROCK_LEGGINGS.getItem());
		new CustomRecipe("Rock_Boots", STONE, AIR, STONE, STONE, AIR, STONE, AIR, AIR, AIR, CustomItems.ROCK_BOOTS.getItem());
	}
	/**
	 * Deprecated! Please use {@link CustomRecipe#newShapedRecipe(ItemStack, String)}!
	 * 
	 * @param is The result of the recipe
	 * @param name The recipes name space name *must be unique*
	 * @return a new ShapedRecipe
	 */
	@Deprecated
	public static ShapedRecipe newShapedRecipe(ItemStack is, String name) {
		return new ShapedRecipe(new NamespacedKey(Main.getInstance(), name), is);
	}
	/**
	 * Deprecated! Please use {@link CustomRecipe#registerRecipe(Recipe)}!
	 * 
	 * @param r The recipe to be added to the game
	 */
	@Deprecated
	public static void addToGame(Recipe r) {
		Bukkit.addRecipe(r);
	}
}
