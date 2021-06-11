package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.interfaces.Energyable;
import eu.pixliesearth.core.custom.interfaces.IHopperable;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>A class to create a machine</h3>
 *
 */
public abstract class CustomEnergyCrafterMachine extends CustomCrafterMachine implements Energyable {
	/**
	 * Initialises the class
	 */
	public CustomEnergyCrafterMachine() {
		
	}
	/**
	 * The amount of energy the machine can hold
	 */
	public abstract double getCapacity();
	
	@Override
	public ItemStack buildItem() {
		return new ItemBuilder(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/"+getPlayerHeadUUID())) {{
			setGlow(isGlowing());
			setUnbreakable(isUnbreakable());
			if (getDefaultDisplayName()==null) 
					setNoName();
			else 
				setDisplayName(getDefaultDisplayName());
			if (getCustomModelData()!=null) 
				setCustomModelData(getCustomModelData());
			for (Entry<Enchantment, Integer> entry : getDefaultEnchants().entrySet()) 
				addEnchantment(entry.getKey(), entry.getValue());
			if (getDefaultLore()!=null)
				addLoreAll(getDefaultLore());
			for (ItemFlag flag : getItemFlags()) 
				addItemFlag(flag);
			if (!getRarity().equals(Rarity.NONE)) 
				addLoreLine("§fRarity: "+getRarity().getName());
			for (Entry<String, Object> entry : getDefaultNBT().entrySet()) 
				addNBTTag(entry.getKey(), entry.getValue().toString(), NBTTagType.STRING);
			addNBTTag("UUID", getUUID(), NBTTagType.STRING);
			addNBTTag("RARITY", getRarity().getUUID(), NBTTagType.STRING);
			addNBTTag("ENERGY", Double.toString(0), NBTTagType.STRING);
		}}.build();
	}
	
	@Override
	public void onTick(Location loc, Inventory inv, Timer timer) {
		super.onTick(loc, inv, timer);
		inv.setItem(52, buildInfoItem(loc));
	}
	
	public boolean isFull(Location loc) {
		return getContainedPower(loc)>=getCapacity();
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
		map.put("ENERGY", Double.toString(CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location)));
		return map;
	}
	
	public ItemStack buildInfoItem(Location location) {
		return new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§6Energy").addLoreLine("§eContained: "+Methods.convertEnergyDouble(Methods.round(getContainedPower(location), 3))).addLoreLine("§eCapacity: "+Methods.convertEnergyDouble(getCapacity())).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
	}
	
	public double getContainedPower(Location location) {
		return CustomFeatureLoader.getLoader().getHandler().getPowerAtLocation(location);
	}
	
	/**
	 * Called to check if the item can be crafted
	 * 
	 * @param location The {@link CustomCrafterMachine} {@link Location}
	 * @param customRecipe The current {@link CustomRecipe}
	 * @return If the crafting can go ahead
	 */
	@Override
	public boolean hasCost(Location location, CustomRecipe customRecipe) {
		return getContainedPower(location)>=customRecipe.getEnergyCost();
	}
	/**
	 * Called to take the cost of the crafting
	 * 
	 * @param location The {@link CustomCrafterMachine} {@link Location}
	 * @param customRecipe The current {@link CustomRecipe}
	 */
	@Override
	public void takeCost(Location location, CustomRecipe customRecipe) {
		CustomFeatureLoader.getLoader().getHandler().removePowerFromLocation(location, customRecipe.getEnergyCost());
	}

}
