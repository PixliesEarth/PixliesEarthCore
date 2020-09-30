package eu.pixliesearth.core.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import eu.pixliesearth.core.objects.Profile;
import eu.pixliesearth.nations.entities.nation.Era;
import lombok.Getter;

public class CraftingListener implements Listener {
	
	@EventHandler
	public void CraftItemEvent(CraftItemEvent event) {
		if (event.getCurrentItem()==null || event.getWhoClicked()==null || !(event.getWhoClicked() instanceof Player)) 
			return;
		Era era = Recipes.getEraFromResult(event.getCurrentItem());
		if (era!=null) 
			if (era.canAccess(Profile.get(event.getWhoClicked().getUniqueId()).getCurrentNation())) 
				event.setCancelled(true);
	}
	
	public static enum Recipes {
		Test(Era.ANCIENT, new ItemStack(Material.OAK_PLANKS));
		
		@Getter protected Era era;
		@Getter protected ItemStack item;
		
		Recipes(Era era, ItemStack item) {
			this.era = era;
			this.item = item;
		}
		
		Recipes(Era era, Recipe recipe) {
			this.era = era;
			this.item = recipe.getResult();
		}
		
		public static Recipes getFromResult(ItemStack result) {
			for (Recipes r : Recipes.values()) 
				if (r.getItem().equals(result)) 
					return r;
			return null;
		}
		
		public static Era getEraFromResult(ItemStack result) {
			Recipes r = getFromResult(result);
			if (r!=null) 
				return r.getEra();
			else 
				return null;
		}
	}
}
