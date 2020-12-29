package eu.pixliesearth.core.custom.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomLiquidHandler;
import eu.pixliesearth.core.custom.interfaces.ILiquidable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;

public class EnergyBlockOilFabricator extends CustomEnergyBlock implements ILiquidable {
	
	public EnergyBlockOilFabricator() {
		
	}
	
	public double getCapacity() {
		return 10000D;
	}
	
	@Override
	public Material getMaterial() {
		return Material.BROWN_MUSHROOM_BLOCK;
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Oil Pump";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Oil_Fabricator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    private final double energyPerAction = 2;
    
    @Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
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
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(map.get("OIL"));
			for (Object key : obj.keySet()) {
				if (key instanceof String) {
					String string = (String) key;
					if (string.contains(", ")) {
						String[] strings = string.split(", ");
						int value = (int) obj.get(key);
						oilTable.put(((strings[0].startsWith("-")) ? -(Integer.parseUnsignedInt(strings[0].replaceAll("-", ""))) : Integer.parseUnsignedInt(strings[0])), ((strings[1].startsWith("-")) ? -(Integer.parseUnsignedInt(strings[1].replaceAll("-", ""))) : Integer.parseUnsignedInt(strings[1])), value);
					}
				}
			}
		} catch (ParseException e) {
			System.err.println("Failed to load oil map!");
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
		JSONObject obj = new JSONObject();
		obj.put(oilID, Integer.toString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, oilID)));
		map.put("LIQUID", obj.toJSONString());
		JSONObject obj2 = new JSONObject();
		for (Cell<Integer, Integer, Integer> cell : oilTable.cellSet()) {
			obj2.put(cell.getRowKey()+", "+cell.getColumnKey(), cell.getValue());
		}
		return map;
	}
	
	private Table<Integer, Integer, Integer> oilTable = HashBasedTable.create();
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomLiquidHandler l = CustomLiquidHandler.getCustomLiquidHandler();
		if (inv==null) return;
		inv.setItem(25, buildInfoItem(loc));
		int remainingOil = oilInChunk(loc);
		if (remainingOil<=0) {
			inv.setItem(12, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cOut of oil!").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
		} else {
			if (getCapacity(loc)<=energyPerAction) {
				inv.setItem(12, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cOut of energy!").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
			} else {
				inv.setItem(12, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§aExtracting of oil!").addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
				l.addLiquidTo(loc, oilID, 1);
				takeOil(loc, 1);
				h.removePowerFromLocation(loc, energyPerAction);
			}
		}
		inv.setItem(14, new ItemBuilder(Material.BROWN_STAINED_GLASS_PANE).setDisplayName("§aOil info").addLoreLine("§aOil: "+l.getLiquidContentsAtAtBasedOnUUID(loc, oilID)).addLoreLine("§aTotal Oil: "+remainingOil).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build());
		ItemStack itemStack = inv.getItem(13);
		if (itemStack==null) return;
		if (CustomItemUtil.getUUIDFromItemStack(itemStack).equals("Pixlies:Canister")) {
			if (l.getLiquidContentsAtAtBasedOnUUID(loc, oilID)>=1000) {
				inv.setItem(13, CustomItemUtil.getItemStackFromUUID("Pixlies:Canister_Oil"));
				l.removeLiquidFrom(loc, oilID, 1000);
			}
		}
	}
	
	private Random random = new Random();
	
	private int oilInChunk(Location loc) {
		if (!loc.getWorld().getEnvironment().equals(Environment.NORMAL)) return 0;
		Chunk chunk = loc.getChunk();
	    Integer oil = oilTable.get(chunk.getX(), chunk.getZ());
	    if (oil==null) {
	    	Biome biome = loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	    	switch (biome) {
	    	case DESERT :
	    	case DESERT_HILLS :
	    		oil = 1000 + (random.nextInt(3000)+1);
	    		break;
	    	case OCEAN :
	    	case COLD_OCEAN :
	    	case LUKEWARM_OCEAN :
	    	case FROZEN_OCEAN :
	    	case WARM_OCEAN :
	    		oil = 1000 + (random.nextInt(1500)+1);
	    		break;
	    	case DEEP_COLD_OCEAN :
	    	case DEEP_FROZEN_OCEAN :
	    	case DEEP_LUKEWARM_OCEAN :
	    	case DEEP_OCEAN :
	    	case DEEP_WARM_OCEAN :
	    		oil = 1000 + (random.nextInt(3000)+1);
	    		break;
	    	case DESERT_LAKES :
	    		oil = 1000 + (random.nextInt(4000)+1);
	    		break;
	    	default :
	    		oil = 350 + (random.nextInt(750)+1);
	    		break;
	    	}
	    	oilTable.put(chunk.getX(), chunk.getZ(), oil);
	    	return oil;
	    } else {
	    	return oil;
	    }
	}
    
	private void takeOil(Location loc, int amount) {
		if (!loc.getWorld().getEnvironment().equals(Environment.NORMAL)) return;
		Chunk chunk = loc.getChunk();
		oilTable.put(chunk.getX(), chunk.getZ(), (oilTable.get(chunk.getX(), chunk.getZ())-amount));
	}
	
    @Override
    public boolean InventoryClickEvent(InventoryClickEvent event) {
    	if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return false;
		if (isUnclickable(event.getCurrentItem())) return true;
    	return false;
    }
    
    @Override
	public Inventory getInventory() {
    	Inventory inv = Bukkit.createInventory(null, 27, getInventoryTitle());
		for (int i = 0; i < 27; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(13);
		return inv;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getLiquidCapacities() {
		return new ConcurrentHashMap<String, Integer>(){private static final long serialVersionUID = -3992298522257305061L;{
			put(oilID, 1000);
		}};
	}
	
	@Override
	public boolean BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
		CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(event.getBlock().getLocation(), getLiquidCapacities().keySet());
		return super.BlockPlaceEvent(event);
	}
}
