package eu.pixliesearth.core.custom;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Map.Entry;
/**
 * 
 * @author BradBot_1
 *
 * <h3>A class to create custom armours</h3>
 *
 */
public class CustomArmour extends CustomItem {
	/**
	 * Initiates the class
	 */
	public CustomArmour() {
		
	}
	/**
	 * The {@link CustomArmour}'s armour value
	 * 
	 * @return The armour value
	 */
	public double getArmour() {
		return 0.0;
	}
	/**
	 * The {@link CustomArmour}'s armour toughness value
	 * 
	 * @return The armour toughness
	 */
	public double getArmourToughness() {
		return 0.0;
	}
	/**
	 * Called when the a player wearing the item is hit
	 * 
	 * @param event The {@link EntityDamageEvent} that has occurred
	 * @return If to cancel the event
	 */
	public boolean EntityDamageEvent(EntityDamageEvent event) {return false;}
	/**
	 * Called when the player equips or unequips the armour
	 * 
	 * @param event The {@link InventoryClickEvent} that has occurred
	 * @return If to cancel the event
	 */
	public boolean ArmourEquipEvent(InventoryClickEvent event) { return false; }
	/**
	 * Called when the player attacks someone while wearing the custom armour
	 * 
	 * @param event The {@link EntityDamageByEntityEvent} that has occurred
	 * @return If to cancel the event
	 */
	public boolean EntityDamageByEntityEvent(EntityDamageByEntityEvent event) { return false; }
	/**
	 * @return Build the {@link CustomItem} into an {@link ItemStack} that can be used ingame
	 */
	@Override
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
				setArmour(getArmour());
				setArmourToughness(getArmourToughness());
				if (!getRarity().equals(Rarity.NONE)) 
					addLoreLine("§fRarity: "+getRarity().getName());
				for (Entry<String, Object> entry : getDefaultNBT().entrySet()) 
					addNBTTag(entry.getKey(), entry.getValue().toString(), NBTTagType.STRING);
				addNBTTag("UUID", getUUID(), NBTTagType.STRING);
				addNBTTag("RARITY", getRarity().getUUID(), NBTTagType.STRING);
			}}.build();
	}
}
