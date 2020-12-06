package eu.pixliesearth.core.custom.blocks;

import eu.pixliesearth.core.custom.CustomEnergyBlock;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomLiquidHandler;
import eu.pixliesearth.core.custom.interfaces.ILiquidable;
import eu.pixliesearth.core.custom.items.ItemCanister;
import eu.pixliesearth.core.custom.items.ItemCanisterHelium;
import eu.pixliesearth.core.custom.items.ItemCanisterHydrogen;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnergyBlockNuclearGenerator2 extends CustomEnergyBlock implements ILiquidable {
	
	public EnergyBlockNuclearGenerator2() {
		
	}
	
	long timePerAction = 250L;
	double energyPerMB = 5D;
	
	public double getCapacity() {
		return 15000000D;
	}
	
	@Override
	public Material getMaterial() {
		return Material.SHROOMLIGHT;
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Nuclear Reactor";
	}
    
    @Override
    public String getUUID() {
        return "Machine:Nuclear_Generator_Fusion"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
	public ConcurrentHashMap<String, Integer> getLiquidCapacities() {
		return new ConcurrentHashMap<String, Integer>() {private static final long serialVersionUID = 733435669890705899L;{
			put(hydrogenID, 100000);
			put(heliumID, 50000);
		}};
	}
    
    @Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
		CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(location, turnSavableStringIntoLiquidContents(map.get("LIQUID")));
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		map.put("LIQUID", turnLiquidContentsIntoSavableString(CustomLiquidHandler.getCustomLiquidHandler().getLiquidContentsAt(location)));
		return map;
	}
    
    public void makeParticeAt(Location loc, Particle p, int amount) {
    	Bukkit.getScheduler().scheduleSyncDelayedTask(CustomFeatureLoader.getLoader().getInstance(), new Runnable() { @Override public void run() {loc.getWorld().spawnParticle(p, loc.getX(), loc.getY(), loc.getZ(), amount);}}, 0L);
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
		inv.clear(20); // Input slot
		inv.clear(24); // Take slot
		return inv;
	}
    
    @Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		CustomLiquidHandler l = CustomLiquidHandler.getCustomLiquidHandler();
		inv.setItem(43, buildInfoItem(loc)); // Energy
		// inv.setItem(22, buildTempItem(loc)); // Temperature
		inv.setItem(21, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE) {{
			setDisplayName("§bHydrogen");
			addLoreLine("§3ID: "+ILiquidable.convertID(hydrogenID));
			addLoreLine("§3Amount: "+l.getLiquidContentsAtAtBasedOnUUID(loc, hydrogenID));
			addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING);
		}}.build());
		inv.setItem(23, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE) {{
			setDisplayName("§bHelium");
			addLoreLine("§3ID: "+ILiquidable.convertID(heliumID));
			addLoreLine("§3Amount: "+l.getLiquidContentsAtAtBasedOnUUID(loc, heliumID));
			addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING);
		}}.build());
		if (timer==null) {
			h.registerTimer(loc, new Timer(250L)); // A quarter of a second per action
			ItemStack is = inv.getItem(20);
			if (is!=null) {
				if (h.getCustomItemFromItemStack(is) instanceof ItemCanisterHydrogen) {
					inv.setItem(20, h.getItemStackFromClass(ItemCanister.class));
					l.addLiquidTo(loc, hydrogenID, 1000);
				}
			}
			ItemStack is2 = inv.getItem(24);
			if (is2!=null) {
				if (h.getCustomItemFromItemStack(is2) instanceof ItemCanister) {
					if (l.getLiquidContentsAtAtBasedOnUUID(loc, heliumID)>=1000) {
						inv.setItem(24, h.getItemStackFromClass(ItemCanisterHelium.class));
						l.removeLiquidFrom(loc, heliumID, 1000);
					}
				}
			}
			if (l.getLiquidContentsAtAtBasedOnUUID(loc, hydrogenID)>1) {
				l.removeLiquidFrom(loc, hydrogenID, 2);
				h.addPowerToLocation(loc, timePerAction);
				l.addLiquidTo(loc, heliumID, 1);
			}
		} else {
			if (timer.hasExpired()) {
				h.unregisterTimer(loc);
			} else {
				// Do nothing
			}
		}
    }
    
    public ItemStack buildWaterItem(Location location, Inventory inv) {
		int i = 0;
		for (Block b : getSurroundingBlocks(location)) 
			if (b!=null) 
				if (b.getType().equals(Material.WATER) || b.getType().equals(Material.ICE) || b.getType().equals(Material.FROSTED_ICE) || b.getType().equals(Material.BLUE_ICE))
					i++;
		return new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayName("§bCooling Information").addLoreLine("§3Surrounding water sources: "+Integer.toString(i)).addLoreLine("§3Coolants: "+"?"/*TODO: make coolant*/).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
    }
    
    @Override
    public boolean BlockPlaceEvent(BlockPlaceEvent event) {
    	CustomLiquidHandler.getCustomLiquidHandler().registerLiquidContents(event.getBlock().getLocation(), getLiquidCapacities().keySet());
    	return super.BlockPlaceEvent(event);
    }
}
