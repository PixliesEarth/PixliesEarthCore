package eu.pixliesearth.core.custom;

import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>The base of a custom item that uses energy!</h3>
 *
 * @apiNote To create a custom item extend this class then register it
 * 
 */
public abstract class CustomEnergyItem extends CustomItem implements Energyable {
	/**
	 * Initiates the class
	 */
	public CustomEnergyItem() {
		
	}
	/**
	 * @return Build the {@link CustomItem} into an {@link ItemStack} that can be used ingame
	 */
	public ItemStack buildItem() {
		return new ItemBuilder(getMaterial()) {{
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
				addLoreLine("§fRarity: "+getRarity().getName());
				for (Entry<String, Object> entry : getDefaultNBT().entrySet()) 
					addNBTTag(entry.getKey(), entry.getValue().toString(), NBTTagType.STRING);
				addNBTTag("UUID", getUUID(), NBTTagType.STRING);
				addNBTTag("RARITY", getRarity().getUUID(), NBTTagType.STRING);
				addNBTTag("ENERGY", Double.toString(0), NBTTagType.STRING);
			}}.build();
	}
}
