package eu.pixliesearth.core.custom;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>The base of a custom recipe!</h3>
 * 
 */
public class CustomRecipes {
	
	protected @Getter JavaPlugin instance;
	
	public CustomRecipes(JavaPlugin javaPlugin) {
		this.instance = javaPlugin;
		Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, new Runnable() {@Override public void run() {load();}});
		}
	
	public void load() {
		//RegisterRecipe(new ShapedRecipe(getNamespacedKey("Tin_Block"), CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Block")) {{shape("aaa","aaa","aaa");setIngredient('a', CustomItemUtil.getItemStackFromUUID("Pixlies:Tin_Ingot"));}});
	}
	
	public NamespacedKey getNamespacedKey(String name) {
		return new NamespacedKey(getInstance(), name);
	}
	
	public void RegisterRecipe(Recipe recipe) {
		Bukkit.addRecipe(recipe);
		System.out.println("Registered the recipe: "+recipe.getResult().getItemMeta().getDisplayName());
	}
	
}
