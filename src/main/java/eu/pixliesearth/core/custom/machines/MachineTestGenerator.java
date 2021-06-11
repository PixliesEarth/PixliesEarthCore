package eu.pixliesearth.core.custom.machines;

import eu.pixliesearth.core.custom.CustomFeatureLoader;
import eu.pixliesearth.core.custom.CustomFuel;
import eu.pixliesearth.core.custom.CustomGeneratorMachine;
import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MachineTestGenerator extends CustomGeneratorMachine {
	
	public MachineTestGenerator() {
		
	}
	
	public double getCapacity() {
		return 10000D;
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		if (inv==null) return;
		inv.setItem(getInfoSlot(), buildInfoItem(loc));
		if (timer==null) {
			ItemStack fuel = inv.getItem(getInputSlot());
			if (fuel==null || fuel.getType().equals(Material.AIR)) {
				inv.setItem(getBurningSlot(), CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				return;
			}
			String id = CustomItemUtil.getUUIDFromItemStack(fuel);
			if (id==null) return;
			CustomItem ci = CustomItemUtil.getCustomItemFromUUID(id);
			if (ci == null || !(ci instanceof CustomFuel)) return;
			CustomFuel cf = (CustomFuel)ci;
			CustomFeatureLoader.getLoader().getHandler().registerTimer(loc, new Timer(cf.getBurnTime()));
			inv.setItem(getBurningSlot(), new ItemBuilder(CustomItemUtil.getItemStackFromUUID(CustomItemUtil.getUUIDFromItemStack(fuel))).addNBTTag("EXTRA", "BURNING", NBTTagType.STRING).build());
			fuel.setAmount(fuel.getAmount()-1);
			inv.setItem(getInputSlot(), fuel);
		} else {
			if (timer.hasExpired()) {
				CustomFeatureLoader.getLoader().getHandler().unregisterTimer(loc);
				inv.setItem(getBurningSlot(), CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
				CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(loc, 50D);
			} else {
				// Do nothing
			}
		}
	}
	
	public boolean InventoryClickEvent(InventoryClickEvent event) {
		if (event.getCurrentItem()==null || event.getCurrentItem().getType().equals(Material.AIR)) return false;
		String s = NBTUtil.getTagsFromItem(event.getCurrentItem()).getString("EXTRA");
		if (s==null) return false;
		if (s.equalsIgnoreCase("BURNING")) return true;
		if (isUnclickable(event.getCurrentItem())) return true;
		return false;
	}
	
	@Override
	public String getPlayerHeadUUID() {
		return "8245a1c3e8d7c3d59d05e3634b04af4cbf8d11b70e2a40e2e6364386db49e737";
	}
	
	@Override
    public String getDefaultDisplayName() {
        return "§6Test Generator";
	}
    
    @Override
    public String getUUID() {
        return "Machine:TestGenerator"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
