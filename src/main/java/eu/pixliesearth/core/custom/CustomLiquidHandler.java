package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.interfaces.ILiquidable;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CustomLiquidHandler {
	
	private static @Getter CustomLiquidHandler customLiquidHandler;
	
	private final @Getter CustomFeatureLoader loader;
	private final @Getter CustomFeatureHandler handler;
	
	private @Getter ConcurrentHashMap<Location, ConcurrentHashMap<String, Integer>> contentsMap;
	
	public CustomLiquidHandler(CustomFeatureLoader loader, CustomFeatureHandler handler) {
		this.loader = loader;
		this.handler = handler;
		this.contentsMap = new ConcurrentHashMap<>();
		customLiquidHandler = this;
	}
	
	public void registerLiquidContents(Location location, ConcurrentHashMap<String, Integer> contents) {
		this.contentsMap.put(location, new ConcurrentHashMap<String, Integer>(){private static final long serialVersionUID = 8250574931729995255L;{contents.forEach((s, v) -> put(ILiquidable.convertID(s), v));}});
	}
	
	public void registerLiquidContents(Location location, Set<String> contents) {
		this.contentsMap.put(location, new ConcurrentHashMap<String, Integer>(){private static final long serialVersionUID = 8250574931729995255L;{contents.forEach((s) -> put(ILiquidable.convertID(s), 0));}});
	}
	
	public boolean addLiquidTo(Location location, String liquid, int amount) {
		if (!this.contentsMap.containsKey(location)) return false;
		this.contentsMap.get(location).put(ILiquidable.convertID(liquid), this.contentsMap.get(location).get(ILiquidable.convertID(liquid))+amount);
		return true;
	}
	
	public boolean removeLiquidFrom(Location location, String liquid, int amount) {
		if (!this.contentsMap.containsKey(location)) return false;
		this.contentsMap.get(location).put(ILiquidable.convertID(liquid), this.contentsMap.get(location).get(ILiquidable.convertID(liquid))-amount);
		return true;
	}
	
	public Map<String, Integer> getLiquidContentsAt(Location location) {
		return this.contentsMap.get(location);
	}
	
	public Integer getLiquidContentsAtAtBasedOnUUID(Location location, String UUID) {
		try {
			return this.contentsMap.get(location).get(ILiquidable.convertID(UUID));
		} catch (Exception e) {
			return null;
		}
	}
}
