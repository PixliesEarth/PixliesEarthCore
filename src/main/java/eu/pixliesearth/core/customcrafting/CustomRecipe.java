package eu.pixliesearth.core.customcrafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import eu.pixliesearth.Main;

public class CustomRecipe {
    
    public CustomRecipe(String name, ItemStack i, ItemStack i2, ItemStack i3, ItemStack i4, ItemStack i5, ItemStack i6, ItemStack i7, ItemStack i8, ItemStack i9, ItemStack result) {
    	ShapedRecipe r = newShapedRecipe(result, name);
    	r.shape("abc",
				"def",
				"ghi");
    	r.setIngredient('a', i);
    	r.setIngredient('b', i2);
    	r.setIngredient('c', i3);
    	r.setIngredient('d', i4);
    	r.setIngredient('e', i5);
    	r.setIngredient('f', i6);
    	r.setIngredient('g', i7);
    	r.setIngredient('h', i8);
    	r.setIngredient('i', i9);
    	registerRecipe(r);
    }
    
    public CustomRecipe(String name, Material i, Material i2, Material i3, Material i4, Material i5, Material i6, Material i7, Material i8, Material i9, ItemStack result) {
    	ShapedRecipe r = newShapedRecipe(result, name);
		r.shape("abc",
				"def",
				"ghi");
    	r.setIngredient('a', i);
    	r.setIngredient('b', i2);
    	r.setIngredient('c', i3);
    	r.setIngredient('d', i4);
    	r.setIngredient('e', i5);
    	r.setIngredient('f', i6);
    	r.setIngredient('g', i7);
    	r.setIngredient('h', i8);
    	r.setIngredient('i', i9);
    	registerRecipe(r);
    }

	public CustomRecipe(String name, RecipeChoice.MaterialChoice i, RecipeChoice.MaterialChoice i2, RecipeChoice.MaterialChoice i3, RecipeChoice.MaterialChoice i4, RecipeChoice.MaterialChoice i5, RecipeChoice.MaterialChoice i6, RecipeChoice.MaterialChoice i7, RecipeChoice.MaterialChoice i8, RecipeChoice.MaterialChoice i9, ItemStack result) {
		ShapedRecipe r = newShapedRecipe(result, name);
		r.shape("abc",
				"def",
				"ghi");
		r.setIngredient('a', i);
		r.setIngredient('b', i2);
		r.setIngredient('c', i3);
		r.setIngredient('d', i4);
		r.setIngredient('e', i5);
		r.setIngredient('f', i6);
		r.setIngredient('g', i7);
		r.setIngredient('h', i8);
		r.setIngredient('i', i9);
		registerRecipe(r);
	}
    
    public CustomRecipe(String name, ArrayList<ItemStack> recipe, ItemStack result) {
    	ShapelessRecipe r = newShaplessRecipe(result, name);
    	for (ItemStack i : recipe) r.addIngredient(i);
    	registerRecipe(r);
    }

    public CustomRecipe(String name, List<RecipeChoice.MaterialChoice> recipe, ItemStack result) {
    	ShapelessRecipe r = newShaplessRecipe(result, name);
    	for (RecipeChoice.MaterialChoice choice : recipe) r.addIngredient(choice);
    	registerRecipe(r);
	}
    
    public static ShapedRecipe newShapedRecipe(ItemStack is, String name) {
		return new ShapedRecipe(new NamespacedKey(Main.getInstance(), name), is);
	}
    
    public static ShapelessRecipe newShaplessRecipe(ItemStack is, String name) {
		return new ShapelessRecipe(new NamespacedKey(Main.getInstance(), name), is);
	}
	
	public static void registerRecipe(Recipe r) {
		Bukkit.addRecipe(r);
	}
}
