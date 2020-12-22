package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EnergyBlockICBMRadar extends CustomEnergyBlock {
	
	public EnergyBlockICBMRadar() {
		
	}
	
	// VARIABLES
	
	public boolean enabled = true;
	public long timePerRefresh = 5255l; // 60555l
	public double energyCostPerOperation = 100D;
	
	// Default Stuff
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Radar";
    }

    @Override
    public String getUUID() {
        return "Pixlies:Missile_Radar"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public Material getMaterial() {
        return Material.STONE_BRICKS;
    }
	
	public double getCapacity() {
		return 1500000D;
	}
    
	// CODE
	
	@Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		for (int i = 0; i < 6*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		return inv;
	}
	
	@Override
    public boolean InventoryClickEvent(InventoryClickEvent event) {
		event.setCancelled(true);
    	return true;
    }
	
	@Override
	public void onTick(Location location, Inventory inventory, Timer timer) {
		if (location==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inventory==null) return;
		if (!enabled) {
			inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§bTime until refresh").addLoreLine("§cThis machine has been disabled by an admin!").build());
			inventory.setItem(52, buildInfoItem(location));
			return;
		}
		inventory.setItem(52, buildInfoItem(location));
		if (timer==null) {
			if (getContainedPower(location)<energyCostPerOperation) {
				h.registerTimer(location, new Timer(1000L)); // One second
				inventory.setItem(49, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§bTime until refresh").addLoreLine("§cNot enough power for this!").build());
			} else {
				renderGuiMap(inventory, location);
				h.registerTimer(location, new Timer(timePerRefresh));
				inventory.setItem(49, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§bTime until refresh").addLoreLine("§cRefreshing!").build());
			}
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(location);
			} else {
				// Do nothing
				inventory.setItem(49, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§bTime until refresh").addLoreLine("§3"+timer.getRemainingAsString()).build());
			}
		}
	}
	
	/*@Deprecated
	public Map<String, Integer> getDanger(World w, int chunkX, int chunkZ) {
		Map<String, Integer> map = new ConcurrentHashMap<>();
		map.put("Pixlies:Missile_Warhead_Block", 0);
		map.put("Pixlies:Missile_Warhead_Block_UNB", 0);
		map.put("Pixlies:Missile_Warhead_Block_UNB_2", 0);
		
		Chunk chunk = w.getChunkAt(chunkX, chunkZ);
		
		final int minX = chunk.getX() << 4;
        final int minZ = chunk.getZ() << 4;
        final int maxX = minX | 15;
        final int maxY = 255;
        final int maxZ = minZ | 15;
		
        for (int y = maxY; y >= 0; --y) {
        	for (int x = minX; x <= maxX; ++x) {
        		for (int z = minZ; z <= maxZ; ++z) {
        			Block b = chunk.getBlock(x & 15, y, z & 15);
                    if (b==null || !b.getType().equals(Material.DISPENSER) && !b.getType().equals(Material.RED_STAINED_GLASS) && !b.getType().equals(Material.GREEN_STAINED_GLASS)) continue;
                    String id = CustomItemUtil.getUUIDFromLocation(b.getLocation());
        			if (id==null) continue;
        			if (id.equalsIgnoreCase("Pixlies:Missile_Warhead_Block")) {
        				//map.compute("Pixlies:Missile_Warhead_Block", (k, v) -> v+1);
        				if (map.containsKey("Pixlies:Missile_Warhead_Block")) {
        					int i = map.get("Pixlies:Missile_Warhead_Block");
        					map.remove("Pixlies:Missile_Warhead_Block");
        					map.put("Pixlies:Missile_Warhead_Block", i+1);
        				} else {
        					map.put("Pixlies:Missile_Warhead_Block", 1);
        				}
        			} else if (id.equalsIgnoreCase("Pixlies:Missile_Warhead_Block_UNB")) {
        				//map.compute("Pixlies:Missile_Warhead_Block_UNB", (k, v) -> v+1);
        				if (map.containsKey("Pixlies:Missile_Warhead_Block_UNB")) {
        					int i = map.get("Pixlies:Missile_Warhead_Block_UNB");
        					map.remove("Pixlies:Missile_Warhead_Block_UNB");
        					map.put("Pixlies:Missile_Warhead_Block_UNB", i+1);
        				} else {
        					map.put("Pixlies:Missile_Warhead_Block_UNB", 1);
        				}
        			} else if (id.equalsIgnoreCase("Pixlies:Missile_Warhead_Block_UNB_2")) {
        				//map.compute("Pixlies:Missile_Warhead_Block_UNB_2", (k, v) -> v+1);
        				if (map.containsKey("Pixlies:Missile_Warhead_Block_UNB_2")) {
        					int i = map.get("Pixlies:Missile_Warhead_Block_UNB_2");
        					map.remove("Pixlies:Missile_Warhead_Block_UNB_2");
        					map.put("Pixlies:Missile_Warhead_Block_UNB_2", i+1);
        				} else {
        					map.put("Pixlies:Missile_Warhead_Block_UNB_2", 1);
        				}
        			} else {
        				continue;
        			}
        		}
        	}
        }
        
		return map;
	}*/
	
	public Map<String, Integer> getDanger(World w, int chunkX, int chunkZ) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Map<String, Integer> map = new ConcurrentHashMap<>();
		map.put("Pixlies:Missile_Warhead_Block", h.getLocationsOfCustomBlockInChunk("Pixlies:Missile_Warhead_Block", w.getChunkAt(chunkZ)).size());
		map.put("Pixlies:Missile_Warhead_Block_UNB", h.getLocationsOfCustomBlockInChunk("Pixlies:Missile_Warhead_Block_UNB", w.getChunkAt(chunkX, chunkZ)).size());
		map.put("Pixlies:Missile_Warhead_Block_UNB_2", h.getLocationsOfCustomBlockInChunk("Pixlies:Missile_Warhead_Block_UNB_2", w.getChunkAt(chunkX, chunkZ)).size());
		return map;
	}
	
	
	// Not needed anymore
	@Deprecated
	public int method(String id, World w, int chunkX, int chunkZ) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Set<Location> set = h.getLocationsOfCustomBlock(id);
		int i = 0;
		for (Location location : set) {
			if (location.getWorld().getUID().equals(w.getUID())) continue;
			if (!(location.getChunk().getX()==chunkX) || !(location.getChunk().getZ()==chunkZ)) continue;
			i += 1;
		}
		// System.out.println("*-----*\n"+id+" "+i+"\n"+w.getName()+","+chunkX+","+chunkZ+"\n*-----*");
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public void renderGuiMap(Inventory inv, Location location) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		Bukkit.getScheduler().scheduleAsyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), 
		new Runnable() {
			
			@Override
			public void run() {
				final int[] ints = {45,46,47,48,49,50,51,52,53}; // 0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,
				
				for (int i = 0; i < 6*9; i++)
					inv.getItem(i).setType(Material.BROWN_STAINED_GLASS_PANE);
				
				for (int i : ints) 
					inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				
				inv.setItem(52, buildInfoItem(location));
				
		        final int height = 2;
		        final int width = 4;
		        final int playerCX = location.getChunk().getX();
		        final int playerCZ = location.getChunk().getZ();
		        final World world = location.getWorld();
		        int i = 0;
		        for (int row = height; row >= -height; row--) {
		        	for (int x = width; x >= -width; x--) {
		        		final int chunkX = playerCX - x, chunkZ = playerCZ - row;
		        		if (getContainedPower(location)>=energyCostPerOperation) {
		        			final int i2 = i; // cast i to a final
		        			Bukkit.getScheduler().scheduleAsyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), 
		        			new Runnable() {
								@Override
								public void run() {
									inv.setItem(i2, getRadarItem(chunkX, chunkZ, getDanger(world, chunkX, chunkZ)));
								}
		        			}, 1l);
		        			h.removePowerFromLocation(location, energyCostPerOperation);
		        			i++;
		        		}
		            }
		        }
			}
			
		}, 1l);
    } 
	
	// UTILS
	
	public String colorCodeMissileAmount(int missileAmount) {
		return (missileAmount==0) ? "§a"+missileAmount : (missileAmount<=3) ? "§e"+missileAmount : (missileAmount<=5) ? "§c"+missileAmount : "§c§l"+missileAmount;
	}
	
	public ItemStack getRadarItem(int chunkX, int chunkZ, Map<String, Integer> dangerMap) {
		Material m = (dangerMap.get("Pixlies:Missile_Warhead_Block_UNB_2")!=0) ? Material.RED_STAINED_GLASS_PANE : (dangerMap.get("Pixlies:Missile_Warhead_Block_UNB")!=0) ? Material.ORANGE_STAINED_GLASS_PANE : (dangerMap.get("Pixlies:Missile_Warhead_Block")!=0) ? Material.YELLOW_STAINED_GLASS_PANE : Material.GRAY_STAINED_GLASS_PANE;
		return new ItemBuilder(m)
				.setDisplayName("§b" + chunkX + "§8, §b" + chunkZ)
				.addLoreLine("§3Missiles: " + colorCodeMissileAmount(dangerMap.get("Pixlies:Missile_Warhead_Block")))
				.addLoreLine("§3Launching Missiles: " + colorCodeMissileAmount(dangerMap.get("Pixlies:Missile_Warhead_Block_UNB")))
				.addLoreLine("§3Launched Missiles: " + colorCodeMissileAmount(dangerMap.get("Pixlies:Missile_Warhead_Block_UNB_2")))
				.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
				.build();
	}
	
	// SAVING / LOADING
	
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		return map;
	}
    
}
