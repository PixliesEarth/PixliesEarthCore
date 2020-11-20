package eu.pixliesearth.core.custom.machines;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mozilla.javascript.ImporterTopLevel;

import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
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

public class EnergyMachineElectrolyser extends CustomEnergyCrafterMachine implements ILiquidable {
	
	public EnergyMachineElectrolyser() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "8cbca012f67e54de9aee72ff424e056c2ae58de5eacc949ab2bcd9683cec";
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Electrolyser";
    }

    @Override
    public String getUUID() {
        return "Machine:Electrolyser"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 5*9, getInventoryTitle());
		for (int i = 0; i < 5*9; i++) 
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(20); // Input slot
		return inv;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inv==null) return;
		inv.setItem(43, buildInfoItem(loc));
		if (timer==null) {
			h.registerTimer(loc, new Timer(200L)); // One fifth of a second
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
			} else {
				// Do nothing
			}
		}
	}
	
	public ItemStack buildWaterItem(Location location) {
		return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
				.setDisplayName("§bInput Information")
				.addLoreLine("§3"+1+": "+Methods.convertLiquidDouble(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, waterID)))
				.addLoreLine("§fRight-click to empty")
				.addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING)
				.build();
    }
	
	public String getInput(Location location) {
		return null;
	}
	
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
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
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		JSONObject obj = new JSONObject();
		obj.put(waterID, Integer.toString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, waterID)));
		obj.put(hydrogenID, Integer.toString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, hydrogenID)));
		obj.put(oxygenID, Integer.toString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAtAtBasedOnUUID(location, oxygenID)));
		map.put("LIQUID", obj.toJSONString());
		return map;
	}
	
	@Override
    public boolean BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
    	CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(event.getBlock().getLocation(), getLiquidCapacities().keySet()); // Give default values
    	return super.BlockPlaceEvent(event);
    }
	
	@Override
	public double getCapacity() {
		return 150000D;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getLiquidCapacities() {
		return new ConcurrentHashMap<String, Integer>(){private static final long serialVersionUID = -2249576370075466384L;{
			put(waterID, 100000);
			put(hydrogenID, 50000);
			put(oxygenID, 50000);
		}};
	}
}
