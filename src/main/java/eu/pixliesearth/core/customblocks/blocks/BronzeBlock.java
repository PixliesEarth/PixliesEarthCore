package eu.pixliesearth.core.customblocks.blocks;

import eu.pixliesearth.core.customblocks.CustomBlock;
import eu.pixliesearth.core.customitems.ci.blocks.BlockBronze;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BronzeBlock extends CustomBlock {
	
	public BronzeBlock(UUID id, Location location) {
		super(id, location);
	}
	
	@Override
	public ItemStack getItem() {
		return BlockBronze.item;
	}
	
	@Override
	public String getTitle() {
		return new BlockBronze().getTitle();
	}
}
