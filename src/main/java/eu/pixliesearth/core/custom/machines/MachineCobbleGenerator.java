package eu.pixliesearth.core.custom.machines;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.CustomCrafterMachine;
import eu.pixliesearth.core.custom.CustomEnergyCrafterMachine;
import eu.pixliesearth.core.custom.CustomFeatureHandler;
import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.MinecraftMaterial;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;

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
		inv.setItem(53, new ItemBuilder(MinecraftMaterial.BARRIER.getMaterial()).setDisplayName("§cClose").addNBTTag("EXTRA", "CLOSE", NBTTagType.STRING).build()); // Close item (back)
		return inv;
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
}
