package eu.pixliesearth.utils;

import static org.bukkit.Material.REDSTONE;
import static org.bukkit.Material.REDSTONE_BLOCK;
import static org.bukkit.Material.STONE;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import eu.pixliesearth.Main;
import eu.pixliesearth.core.machines.Machine.MachineType;

public class CustomRecipies {
	public static void register(Plugin p) {
		ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "Machine_Crafter"), MachineType.MACHINE_CRAFTER.getItem());
		r.shape("r", "s", "r", 
				"s", "b", "s", 
				"r", "s", "r");
		r.setIngredient('r', REDSTONE);
		r.setIngredient('b', REDSTONE_BLOCK);
		r.setIngredient('r', STONE);
		addToGame(r);
	}
	
	public static ShapedRecipe newShapedRecipe(ItemStack is, String name) {
		return new ShapedRecipe(new NamespacedKey(Main.getInstance(), name), is);
	}
	
	public static void addToGame(Recipe r) {
		Bukkit.addRecipe(r);
	}
}
