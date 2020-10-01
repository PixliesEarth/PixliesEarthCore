package eu.pixliesearth.core.customitems.ci.blocks;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.customitems.CustomItem;
import eu.pixliesearth.utils.ItemBuilder;

public class BlockBronze implements CustomItem {
	
	public static ItemStack item = new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(new BlockBronze().getTitle()).setCustomModelData(25).build();
	
	public String getTitle() {
		return "§6BronzeBlock";
	}
	
	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public String getName() {
		return getItem().getItemMeta().getDisplayName();
	}

	@Override
	public List<String> getLore() {
		List<String> lore = getItem().getItemMeta().getLore();
        return lore;
	}

	@Override
	public ItemStack getStatic(int durability) {
		return item;
	}

	@Override
	public boolean enchantable() {
		return false;
	}

	@Override
	public void onInteract(PlayerInteractEvent event) 
	{
	}

}
