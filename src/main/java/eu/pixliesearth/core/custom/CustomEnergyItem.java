package eu.pixliesearth.core.custom;

import eu.pixliesearth.core.custom.interfaces.Energyable;
import eu.pixliesearth.utils.CustomItemUtil;
import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Map.Entry;

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
	
	public boolean isFull(ItemStack itemStack) {
		Double d = getContainedPower(itemStack);
		Double d2 = getCapacity(itemStack);
		return (d==null||d2==null) ? true : d>=d2; // If null send a fake true value
	}
	
	public ItemStack removeEnergy(ItemStack itemStack, double amount) {
		if (getContainedPower(itemStack)<amount) return itemStack;
		NBTTags tags = NBTUtil.getTagsFromItem(itemStack);
		tags.addTag("ENERGY", Double.toString(Double.parseDouble(NBTUtil.getTagsFromItem(itemStack).getString("ENERGY"))-amount), NBTTagType.STRING);
		return NBTUtil.addTagsToItem(itemStack, tags);
	}
	
	public ItemStack giveEnergy(ItemStack itemStack, double amount) {
		if (isFull(itemStack)) return itemStack;
		NBTTags tags = NBTUtil.getTagsFromItem(itemStack);
		tags.addTag("ENERGY", Double.toString(Double.parseDouble(NBTUtil.getTagsFromItem(itemStack).getString("ENERGY"))+amount), NBTTagType.STRING);
		return NBTUtil.addTagsToItem(itemStack, tags);
	}
	
	public Double getCapacity(ItemStack itemStack) {
		CustomItem c = CustomItemUtil.getCustomItemFromUUID(CustomItemUtil.getUUIDFromItemStack(itemStack));
		if (c==null) return null;
		if (c instanceof Energyable) {
			return ((Energyable)c).getCapacity();
		} else {
			return null;
		}
	}
	
	public Double getContainedPower(ItemStack itemStack) {
		return Double.parseDouble(NBTUtil.getTagsFromItem(itemStack).getString("ENERGY"));
	}
}
