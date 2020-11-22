package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomLiquidHandler;
import eu.pixliesearth.core.custom.interfaces.ILiquidable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.Methods;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockOilBurner extends CustomEnergyBlock implements ILiquidable {
	
	public EnergyBlockOilBurner() {
		
	}
	
	public double getCapacity() {
		return 1000000D;
	}
	
	@Override
	public ConcurrentHashMap<String, Integer> getLiquidCapacities() {
		return new ConcurrentHashMap<String, Integer>(){private static final long serialVersionUID = 4369338094535742708L;{
			put(waterID, 50000);
			put(oilID, 50000);
		}};
	}
	
	@Override
	public Material getMaterial() {
		return Material.BROWN_MUSHROOM_BLOCK;
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Oil Burner";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Oil_Burner"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
		CustomFeatureLoader.getLoader().getHandler().addTempratureToLocation(location, Double.parseDouble(map.get("TEMP")));
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(map.get("LIQUID"));
			CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(location, new ConcurrentHashMap<String, Integer>(){private static final long serialVersionUID = 2953303923607067655L;{
				for (Object key : obj.keySet()) {
					try {
						put((String) key, Integer.parseInt((String) obj.get(obj)));
					} catch (Exception e) {
						put((String) key, 0);
					}
				}
			}});
		} catch (ParseException e) {
			CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(location, getLiquidCapacities().keySet());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		map.put("TEMP", Double.toString(getTemprature(location)));
		JSONObject obj = new JSONObject();
		obj.put(waterID, Integer.toString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, waterID)));
		obj.put(oilID, Integer.toString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, oilID)));
		map.put("LIQUID", obj.toJSONString());
		return map;
	}
    
    @Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		inv.setItem(43, buildInfoItem(loc));
		inv.setItem(23, buildWaterItem(loc));
		inv.setItem(22, buildTempItem(loc));
		inv.setItem(21, buildOilItem(loc));
		if (timer==null) {
			h.registerTimer(loc, new Timer(250L)); // A quarter of a second per action
			CustomLiquidHandler l = CustomLiquidHandler.getCustomLiquidHandler();
			try {
				ItemStack oil = inv.getItem(20);
				if (oil!=null && ILiquidable.isBucketFormOf(oil, oilID, false)) {
					if (l.getLiquidContentsAtAtBasedOnUUID(loc, oilID)<getLiquidCapacities().get(oilID)) { // Not full
						l.addLiquidTo(loc, oilID, 1000);
						inv.setItem(20, new ItemStack(Material.BUCKET, 1));
					}
				}
			} catch (Exception ingore) {}
			try {
				ItemStack water = inv.getItem(24);
				if (water!=null && ILiquidable.isBucketFormOf(water, waterID, false)) {
					if (l.getLiquidContentsAtAtBasedOnUUID(loc, waterID)<getLiquidCapacities().get(waterID)) { // Not full
						l.addLiquidTo(loc, waterID, 1000);
						inv.setItem(24, new ItemStack(Material.BUCKET, 1));
					}
				}
			} catch (Exception ingore) {}
			coolTemp(loc);
			try {
				int oil = l.getLiquidContentsAtAtBasedOnUUID(loc, oilID);
				double temp = getTemprature(loc);
				if (oil>0) {
					double powerToAdd = 1.5D;
					double tempToAdd = 1D;
					// All numbers are divided by 4 as this occurs 4 times a second!
					if (temp>500) {
						powerToAdd += 15D/4D;
						tempToAdd += 5D/4D;
					} else if (temp>300) {
						powerToAdd += 35D/4D;
						tempToAdd += 2D/4D;
					} else if (temp>225) {
						powerToAdd += 45D/4D;
						tempToAdd += 1.5D/4D;
					} else if (temp>150) {
						powerToAdd += 50D/4D;
						tempToAdd += 1D/4D;
					} else if (temp>115) {
						powerToAdd += 30D/4D;
						tempToAdd += 2D/4D;
					} else if (temp>100) {
						powerToAdd += 15D/4D;
						tempToAdd += 2D/4D;
					} else if (temp>75) {
						powerToAdd += 5D/4D;
						tempToAdd += 2D/4D;
					} else if (temp>50) {
						powerToAdd += 2D/4D;
						tempToAdd += 1D/4D;
					} else {
						powerToAdd += 1D/4D;
						tempToAdd += 1D/4D;
					}
					h.addTempratureToLocation(loc, tempToAdd);
					h.addPowerToLocation(loc, powerToAdd);
					l.removeLiquidFrom(loc, oilID, 1);
				}
			} catch (Exception ignore) {
				inv.setItem(40, new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
						.setDisplayName("§c§lError")
						.addLoreLine("§cUnable to get liquid values!")
						.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
						.build());
				CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(loc, getLiquidCapacities().keySet()); // Give default values
			}
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
			} else {
				// Do nothing
			}
		}
    }
    
    public void coolTemp(Location loc) {
    	try {
    		CustomLiquidHandler l = CustomLiquidHandler.getCustomLiquidHandler();
    		CustomFeatureHandler h = l.getHandler();
			int water = l.getLiquidContentsAtAtBasedOnUUID(loc, waterID);
			double temp = getTemprature(loc);
			if (temp>0D) {
				if (temp<0.01D) {
					h.removeTempratureFromLocation(loc, temp);
				} else {
					h.removeTempratureFromLocation(loc, 0.01D);
				}
			}
			temp = getTemprature(loc); // update temp
			if (temp > 0D && water > 0) {
				if (temp < 0.05D) {
					h.removeTempratureFromLocation(loc, temp);
				} else {
					l.removeLiquidFrom(loc, waterID, 1);
					h.removeTempratureFromLocation(loc, 0.25D);
				}
			}
		} catch (Exception ignore) {
			CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(loc, getLiquidCapacities().keySet()); // Give default values
		}
    }
    
    @Override
    public boolean InventoryClickEvent(InventoryClickEvent event) {
    	if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return false;
		if (isUnclickable(event.getCurrentItem())) return true;
    	return false;
    }
    
    @Override
	public Inventory getInventory() {
		Inventory inv = Bukkit.createInventory(null, 5*9, getInventoryTitle());
		for (int i = 0; i < 5*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(20); // Oil input
		inv.clear(24); // Water Input
		return inv;
	}
    
    @Override
    public boolean BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
    	CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(event.getBlock().getLocation(), getLiquidCapacities().keySet()); // Give default values
    	return super.BlockPlaceEvent(event);
    }
    
    public double getTemprature(Location location) {
    	Double d = CustomFeatureLoader.getLoader().getHandler().getTempratureAtLocation(location);
    	if (d==null) {
    		CustomFeatureLoader.getLoader().getHandler().addTempratureToLocation(location, 0D);
    	}
    	return (d==null) ? 0.0D : Methods.round(d, 3);
    }
    
    public ItemStack buildWaterItem(Location location) {
		return new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
				.setDisplayName("§bCooling Information")
				.addLoreLine("§3Water: "+Methods.convertLiquidDouble(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, waterID)))
				.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
				.build();
    }
    
    public ItemStack buildOilItem(Location location) {
		return new ItemBuilder(Material.BROWN_STAINED_GLASS_PANE)
				.setDisplayName("§bOil Information")
				.addLoreLine("§3Oil: "+Methods.convertLiquidDouble(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, oilID)))
				.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
				.build();
    }
    
    public ItemStack buildTempItem(Location location) {
		Double temp = getTemprature(location);
		return new ItemBuilder((temp==null) ? Material.GRAY_STAINED_GLASS_PANE : (temp<35) ? Material.PINK_STAINED_GLASS_PANE : (temp<100) ? Material.PURPLE_STAINED_GLASS_PANE : (temp<250) ? Material.GREEN_STAINED_GLASS_PANE : (temp<300) ? Material.YELLOW_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE).setDisplayName((temp==null) ? "§cError!" : "§cTemprature").addLoreLine((temp==null) ? "§cRecieved a null!" : "§c"+temp+"°c").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
	}
}
