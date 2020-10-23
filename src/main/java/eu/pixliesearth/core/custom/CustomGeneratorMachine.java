package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.Timer;
import lombok.Getter;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a machine</h3>
 *
 */
public class CustomGeneratorMachine extends CustomMachine {
	
	private static @Getter int inputSlot = 10;
	private static @Getter int burningSlot = 13;
	private static @Getter int infoSlot = 16;
	
	/**
	 * Initialises the class
	 */
	public CustomGeneratorMachine() {
		
	}
	/**
	 * The amount of energy the machine can hold
	 */
	public double getCapacity() {
		return 100D;
	}
	/**
	 * Called every Minecraft Tick
	 */
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		inv.setItem(getInfoSlot(), buildInfoItem(loc));
	}
	/**
	 * @return The {@link CustomGeneratorMachine}'s {@link Inventory}
	 */
	@Override
	public Inventory getInventory() { 
		Inventory inv = Bukkit.createInventory(null, 3*9, getInventoryTitle());
		for (int i = 0; i < 3*9; i++)
			inv.setItem(i, CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID()));
		inv.clear(getInputSlot());
		inv.clear(getInfoSlot());
		return inv;
	}
	
	public double getContainedPower(Location location) {
		return CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location);
	}
	
	public ItemStack buildInfoItem(Location location) {
		return new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).addLoreLine("§eContained: "+Double.toString(getContainedPower(location))).addLoreLine("§eCapacity: "+Double.toString(getCapacity())).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
	}
	// TODO: notes
	@Override
	public void loadFromSaveData(Inventory inventory, Location location, Map<String, String> map) {
		if (map.get("TIMEREX")!=null && map.get("TIMEREN")!=null)
			CustomFeatureLoader.getLoader().getHandler().registerTimer(location, new Timer(Long.parseLong(map.get("TIMEREX")), Boolean.getBoolean(map.get("TIMEREN"))));
		CustomFeatureLoader.getLoader().getHandler().addPowerToLocation(location, Double.parseDouble(map.get("ENERGY")));
	}
	// TODO: notes
	@Override
	public HashMap<String, String> getSaveData(Location location, Inventory inventory, Timer timer) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (timer!=null) {
			map.put("TIMEREX", Long.toString(timer.getExpiry()));
			map.put("TIMEREN", Boolean.toString(timer.isEnded()));
		}
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		return map;
	}
	// TODO: notes
	public boolean isUnclickable(ItemStack is) {
		return CustomItemUtil.getUUIDFromItemStack(is).equalsIgnoreCase(CustomInventoryListener.getUnclickableItemUUID());
	}
}