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
        return "§6Oil Fabricator";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Oil_Fabricator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
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
		return map;
	}
    
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomLiquidHandler l = CustomLiquidHandler.getCustomLiquidHandler();
		if (inv==null) return;
		inv.setItem(25, buildInfoItem(loc));
		if (getContainedPower(loc)>=getCapacity()) return;
		ItemStack itemStack = inv.getItem(13);
		if (itemStack==null) return;
		if (CustomItemUtil.getUUIDFromItemStack(itemStack).equalsIgnoreCase("Pixlies:Biofuel")) {
			if (!(getContainedPower(loc)>0) || l.getLiquidContentsAtAtBasedOnUUID(loc, oilID)>=1000) return;
			itemStack.setAmount(itemStack.getAmount()-1);
			if (itemStack==null || itemStack.getAmount()<=0) {
				inv.clear(13);
			} else {
				inv.setItem(13, itemStack);
			}
			h.removePowerFromLocation(loc, 2.5);
			l.addLiquidTo(loc, oilID, 1);
		} else if(CustomItemUtil.getUUIDFromItemStack(itemStack).equalsIgnoreCase("Pixlies:Canister")) {
			if (l.getLiquidContentsAtAtBasedOnUUID(loc, oilID)>=1000) {
				inv.setItem(13, CustomItemUtil.getItemStackFromUUID("Pixlies:Canister_Oil"));
				l.removeLiquidFrom(loc, oilID, 1000);
			}
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
}
