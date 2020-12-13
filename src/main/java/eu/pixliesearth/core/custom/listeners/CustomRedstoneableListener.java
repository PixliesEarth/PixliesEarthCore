package eu.pixliesearth.core.custom.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

import eu.pixliesearth.core.custom.CustomBlock;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomListener;
import eu.pixliesearth.core.custom.interfaces.IRedstoneable;

public class CustomRedstoneableListener extends CustomListener {
	
	@EventHandler
	public void BlockRedstoneEvent(BlockRedstoneEvent event) {
		CustomBlock customBlock = CustomFeatureLoader.getLoader().getHandler().getCustomBlockFromLocation(event.getBlock().getLocation());
		if (customBlock instanceof IRedstoneable) {
			IRedstoneable iRedstoneableCustomBlock = (IRedstoneable) customBlock;
			iRedstoneableCustomBlock.onRecievedRedstoneSignal(event.getBlock().getLocation(), event.getNewCurrent(), event);
		}
	}
	
}
