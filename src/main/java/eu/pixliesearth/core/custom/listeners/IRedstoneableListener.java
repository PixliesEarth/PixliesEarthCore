package eu.pixliesearth.core.custom.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.interfaces.IRedstoneable;

public class IRedstoneableListener extends CustomListener {
	
	@EventHandler
	public void BlockRedstoneEvent(BlockRedstoneEvent event) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Block block = event.getBlock();
		Set<Block> blocks = getSurroundingBlocks(block);
		CustomBlock customBlock;
		for (Block block2 : blocks) {
			customBlock = h.getCustomBlockFromLocation(block2.getLocation());
			if (customBlock==null) continue;
			if (customBlock instanceof IRedstoneable) {
				IRedstoneable iRedstoneable = (IRedstoneable) customBlock;
				iRedstoneable.onRecievedRedstoneSignal(block2.getLocation());
			}
		}
	}
	
	private Set<Block> getSurroundingBlocks(Block block){
		Set<Block> blocks = new LinkedHashSet<>();
        for (BlockFace face : BlockFace.values()){
        	blocks.add(block.getRelative(face));
        }
        return blocks;
    }
	
}
