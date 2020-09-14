package eu.pixliesearth.core.customcrafting;

import static org.bukkit.Material.REDSTONE;
import static org.bukkit.Material.REDSTONE_BLOCK;
import static org.bukkit.Material.STONE;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine.MachineType;

public class CustomRecipies {
	/**
	 * Registers some custom recipes
	 */
	public static void register() {
		new CustomRecipe("Machine_Crafter", // Name
				REDSTONE, STONE, REDSTONE, // Row one
				STONE, REDSTONE_BLOCK, STONE, // Row two
				REDSTONE, STONE, REDSTONE, // Row three
				MachineType.MACHINE_CRAFTER.getItem()); // Result
	}
	/**
	 * Deprecated! Please use {@link CustomRecipe#newShapedRecipe(ItemStack, String)}!
	 * 
	 * @param is The result of the recipe
	 * @param name The recipes name space name *must be unqiue*
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
