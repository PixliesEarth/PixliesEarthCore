package eu.pixliesearth.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import lombok.Getter;
import lombok.Setter;

public class ExplosionCalculator {
	
	// NMS
	
	private static final String serverVersion = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	private static final String craftServerVersion = "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	private static Class<?> craftBlockDataClass = null;
	private static Class<?> blockClass = null;
	private static Method getStateFromCraftBlockDataMethod = null;
	private static Method getDurabilityFromBlockMethod = null;
	
	private static void loadNMS() {
		try {
			if (craftBlockDataClass==null) {
				craftBlockDataClass = Class.forName(craftServerVersion+".block.data.CraftBlockData");
			}
			if (blockClass==null) {
				blockClass = Class.forName(serverVersion+".Block");
			}
			if (getStateFromCraftBlockDataMethod==null) {
				getStateFromCraftBlockDataMethod = craftBlockDataClass.getDeclaredMethod("getState", (Class[]) null);
			}
			if (getDurabilityFromBlockMethod==null) {
				getDurabilityFromBlockMethod = blockClass.getDeclaredMethod("getDurability", (Class[]) null);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	// ExplosionCalculator
	
	public final @Getter Location center;
	private final World world;
	public final int radius;
	private final int radiusSQ;
	protected @Getter boolean ready;
	protected @Getter boolean vaporise;
	private @Getter boolean checkedForAir = false;
	private @Getter List<Location> explodeLocations = new ArrayList<>();
	private @Getter float explosiveValue = -1F;
	private @Getter @Setter String deathReason = "just exploded!";
	
	/**
	 * 
	 * @param location
	 * @param radius
	 * @param async
	 */
	public ExplosionCalculator(Location location, int radius, boolean async) {
		this.center = location;
		this.world = location.getWorld();
		this.radius = radius;
		this.vaporise = true;
		this.radiusSQ = radius*radius;
		update(async); // Load
		loadNMS();
	}
	/**
	 * 
	 * @param location
	 * @param radius
	 * @param async
	 * @param explosiveValue
	 */
	@Deprecated
	public ExplosionCalculator(Location location, int radius, boolean async, float explosiveValue) {
		this.center = location;
		this.world = location.getWorld();
		this.radius = radius;
		this.vaporise = false;
		this.radiusSQ = radius*radius;
		this.explosiveValue = explosiveValue*100;
		update(async); // Load
		loadNMS();
	}
	/**
	 * Updates the stored explosion list
	 * 
	 * @apiNote If async is false then it will make sure the block is not air, elsewise it skips this check
	 * 
	 * @param async If the update was called asynchronously
	 */
	public boolean update(boolean async) {
		this.explodeLocations.clear();
		this.ready = false;
		if (isVaporise()) {
			int bx = this.center.getBlockX();
			int by = this.center.getBlockY();
			int bz = this.center.getBlockZ();
			
			for(int x = bx - radius; x <= bx + radius; x++) {
				for(int y = by - radius; y <= by + radius; y++) {
					for(int z = bz - radius; z <= bz + radius; z++) {
						double distanceSQ = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
						if(distanceSQ < radiusSQ) {
							if (async) {
								explodeLocations.add(new Location(this.world, x, y, z));
							} else {
								Location location2 = new Location(this.world, x, y, z);
		                    	Block block = location2.getBlock();
		                    	if (block!=null && !block.getType().equals(Material.AIR)) {
		                    		explodeLocations.add(location2);
		                    	}
		                    }
						}
					}
				}
			}
			this.checkedForAir = !async;
			this.ready = true;
			return true;
		} else {
			if (async) {
				this.checkedForAir = false;
				this.ready = false;
				throw new RuntimeException("ExplosionUtil#ExplosionCalculator#update() cannot be called asynchronously when it has an explosion value");
			} else {
				int bx = this.center.getBlockX();
				int by = this.center.getBlockY();					
				int bz = this.center.getBlockZ();
				
				for(int x = bx - radius; x <= bx + radius; x++) {
					for(int y = by - radius; y <= by + radius; y++) {
						float f = explosiveValue;
						for(int z = bz - radius; z <= bz + radius; z++) {
							if (f<=0) break;
							double distanceSQ = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
							if(distanceSQ < radiusSQ) {
								Location location2 = new Location(this.world, x, y, z);
			                    Block block = location2.getBlock();
			                    if (block!=null && !block.getType().equals(Material.AIR)) {
			                    	try {
			                    		getStateFromCraftBlockDataMethod.setAccessible(true);
										getDurabilityFromBlockMethod.setAccessible(true);
										
										// TODO: Remove the constant caching as it is not needed!
										
										Object craftBlockData = craftBlockDataClass.cast(block.getBlockData());
										
										Object blockState = getStateFromCraftBlockDataMethod.invoke(craftBlockData, (Object[]) null);
										
										Object NMSblock = blockState.getClass().getDeclaredMethod("getBlock", (Class[]) null).invoke(blockState, (Object[]) null);
										
										Object blockDurability = getDurabilityFromBlockMethod.invoke(NMSblock, (Object[]) null);
										
										if (blockDurability instanceof Float) {
											f -= (Float) blockDurability;
										} else if (blockDurability instanceof Integer) {
											f -= (Integer) blockDurability;
										} else if (blockDurability instanceof Double) {
											f -= (Double) blockDurability;
										} else {
											f -= 0;
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
			                    	explodeLocations.add(location2);
			                    }
							}
						}
					}
				}
				this.checkedForAir = true;
				this.ready = true;
				return true;
			}
		}
	}
	
	public void checkForAir() {
		List<Location> list = new ArrayList<>();
		for (Location l : explodeLocations) {
			Block block = l.getBlock();
            if (block!=null && !block.getType().equals(Material.AIR)) {
            	list.add(l);
            }
		}
		explodeLocations = list;
	}
	
	public void explode(boolean fire) {
		if (!isReady()) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Bukkit.getScheduler().scheduleSyncDelayedTask(h.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (!checkedForAir) {
					checkForAir();
				}
				for (Location l : explodeLocations) {
					if (l==null || l.getBlock()==null|| l.getBlock().getType().equals(Material.AIR)) continue;
					if (h.isCustomBlockAtLocation(l)) {
						h.removeCustomBlockFromLocation(l); 
					} else {
						if (fire) {
							if ((new Random().nextInt(10)<3)) {
								l.getBlock().setType(Material.FIRE);
								l.getBlock().getState().update();
							} else {
								l.getBlock().setType(Material.AIR);
							}
						} else {
							l.getBlock().setType(Material.AIR);
						}
					}
				}
			}
			
		}, 1L);
	}
	
}