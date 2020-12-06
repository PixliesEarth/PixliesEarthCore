package eu.pixliesearth.core.custom.machines;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomCrafterMachine;
import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;

public class EnergyMachineBioFuelGenerator extends CustomEnergyCrafterMachine {
	
	public EnergyMachineBioFuelGenerator() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "656634b556caf5382de65038a10e4d79c7c18695048599df74f9c67c1e1e8736";
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Biofuel Generator";
    }

    @Override
    public String getUUID() {
        return "Machine:Biofuel_Generator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    /**
	 * @return The {@link CustomCrafterMachine}'s {@link Inventory}
	 */
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 27, getInventoryTitle());
		for (int i = 0; i < 27; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(13);
		return inv;
	}
	
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		Double energy = CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location);
		map.put("ENERGY", Double.toString((energy==null) ? 0 : energy));
		return map;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inv==null) return;
		inv.setItem(25, buildInfoItem(loc));
		if (getContainedPower(loc)>=getCapacity()) return;
		ItemStack itemStack = inv.getItem(13);
		if (itemStack==null) return;
		if (CustomItemUtil.getUUIDFromItemStack(itemStack).equalsIgnoreCase("Pixlies:Biofuel")) {
			itemStack.setAmount(itemStack.getAmount()-1);
			if (itemStack==null || itemStack.getAmount()<=0) {
				inv.clear(13);
			} else {
				inv.setItem(13, itemStack);
			}
		} else {
			return;
		}
		h.addPowerToLocation(loc, 1D);
	}

	public double getCapacity() {
		return 50000D;
	}
}
