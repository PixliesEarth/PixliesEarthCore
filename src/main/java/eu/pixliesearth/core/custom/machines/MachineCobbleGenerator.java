package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.*;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MachineCobbleGenerator extends CustomEnergyCrafterMachine {
	
	public MachineCobbleGenerator() {
		
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "b5c9762729d48d0a16fe89573bdd2faf50196fea15d49b5a6bfea489be71";
	}

    @Override
    public String getDefaultDisplayName() {
        return "§6Cobblestone Generator";
    }

    @Override
    public String getUUID() {
        return "Machine:Cobble_Generator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    /**
	 * @return The {@link CustomCrafterMachine}'s {@link Inventory}
	 */
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 6*9, getInventoryTitle());
		int[] ints = {0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
		for (int i : ints)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(52);
		return inv;
	}
	
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
		/* int i = Integer.parseInt(map.get("COBBLE"));
		while (i!=0) {
			if (inventory.firstEmpty()==-1) return;
			if (i>=64) {
				inventory.addItem(new ItemStack(Material.COBBLESTONE, 64));
				i -= 64;
			} else {
				inventory.addItem(new ItemStack(Material.COBBLESTONE, 1));
				i -= 1;
			}
		} */
	}
	
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		int i = 0;
		for (ItemStack is : inventory.getContents()) {
			if (is==null) continue;
		    if(CustomItemUtil.getUUIDFromItemStack(is).equals(MinecraftMaterial.COBBLESTONE.getUUID()))
		    	i += is.getAmount();
		}
		map.put("COBBLE", Integer.toString(i));
		return map;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (loc==null) return;
		CustomFeatureHandler h = CustomFeatureLoader.getLoader().getHandler();
		if (inv==null) return;
		inv.setItem(52, buildInfoItem(loc));
		if (timer==null) {
			h.registerTimer(loc, new Timer(500L));
			return;
		} else {
			if (timer.hasExpired()) {
				if (getContainedPower(loc)-1D<=0D) return;
				if (inv.firstEmpty()==-1) return;
				inv.addItem(new ItemStack(Material.COBBLESTONE, 1));
				h.removePowerFromLocation(loc, 1D);
				h.unregisterTimer(loc);
				return;
			} else {
				// Do nothing
				return;
			}
		}
	}

	public double getCapacity() {
		return 100D;
	}
}
