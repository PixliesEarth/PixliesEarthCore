package eu.pixliesearth.core.custom.machines;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomLiquidHandler;
import eu.pixliesearth.core.custom.interfaces.ILiquidable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;

public class EnergyMachineElectrolyser extends CustomEnergyCrafterMachine implements ILiquidable {
	
	public EnergyMachineElectrolyser() {
		
	}
	
	long timePerAction = 200L;
	double energyCost = 50D;
	
	@Override
	public String getPlayerHeadUUID() {
		return "8cbca012f67e54de9aee72ff424e056c2ae58de5eacc949ab2bcd9683cec";
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Water Electrolyser";
    }

    @Override
    public String getUUID() {
        return "Machine:Electrolyser_Water"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public void open(Player player, Location location) {
		Inventory i = CustomFeatureLoader.getLoader().getHandler().getInventoryFromLocation(location);
		if (i==null) {
			player.sendMessage("This machine has no inventory!");
		} else {
			player.openInventory(i);
		}
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
		CustomLiquidHandler l = CustomLiquidHandler.getCustomLiquidHandler();
		if (inv==null) return;
		inv.setItem(43, buildInfoItem(loc));
		if (timer==null) {
			h.registerTimer(loc, new Timer(timePerAction)); // One fifth of a second
			if (getContainedPower(loc)<energyCost) return;
			ItemStack is = inv.getItem(20);
			if (is!=null && ILiquidable.isBucketFormOf(is, waterID, false)) {
				inv.setItem(20, new ItemStack(Material.BUCKET));
				l.addLiquidTo(loc, waterID, 1000);
			}
			inv.setItem(21, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE) {{
					setDisplayName("§bInput Information");
					addLoreLine("§3ID: "+ILiquidable.convertID(waterID)+" / "+waterID);
					addLoreLine("§3Amount: "+l.getLiquidContentsAtAtBasedOnUUID(loc, waterID));
					addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING);
			}}.build());
			inv.setItem(23, new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE) {{
					setDisplayName("§bHydrogen");
					addLoreLine("§3ID: "+hydrogenID);
					addLoreLine("§3Amount: "+l.getLiquidContentsAtAtBasedOnUUID(loc, hydrogenID));
					addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING);
			}}.build());
			inv.setItem(25, new ItemBuilder(Material.PINK_STAINED_GLASS_PANE) {{
					setDisplayName("§bOxygen");
					addLoreLine("§3ID: "+oxygenID);
					addLoreLine("§3Amount: "+l.getLiquidContentsAtAtBasedOnUUID(loc, oxygenID));
					addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING);
			}}.build());
			if (l.getLiquidContentsAtAtBasedOnUUID(loc, waterID)>0) {
				for (String s : ILiquidable.getLiquidContents(waterID)) {
					l.addLiquidTo(loc, s, 1);
				}
				l.removeLiquidFrom(loc, waterID, 1);
				h.removePowerFromLocation(loc, energyCost);
			}
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
			} else {
				// Do nothing
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
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
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
