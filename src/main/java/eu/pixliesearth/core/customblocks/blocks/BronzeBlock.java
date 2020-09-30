package eu.pixliesearth.core.customblocks.blocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.customblocks.CustomBlock;
import eu.pixliesearth.utils.ItemBuilder;

public class BronzeBlock extends CustomBlock {
	
	public static ItemStack item = new ItemBuilder(Material.GOLD_BLOCK).setDisplayName(new BronzeBlock().getTitle()).setCustomModelData(25).build();
	
	@Override
	public ItemStack getItem() {
		return item;
	}
	
	@Override
	public String getTitle() {
		return "BronzeBlock";
	}
}
