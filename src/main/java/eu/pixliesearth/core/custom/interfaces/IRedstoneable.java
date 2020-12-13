package eu.pixliesearth.core.custom.interfaces;

import org.bukkit.Location;
import org.bukkit.event.block.BlockRedstoneEvent;

public interface IRedstoneable {

	public void onRecievedRedstoneSignal(Location location, int strength, BlockRedstoneEvent event);

}
