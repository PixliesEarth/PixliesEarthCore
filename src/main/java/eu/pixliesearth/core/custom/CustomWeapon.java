package eu.pixliesearth.core.custom;

import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
/**
 * 
 * @author BradBot_1
 *
 * <h3>A class to create custom armours</h3>
 *
 */
public class CustomWeapon extends CustomItem {
	/**
	 * Initiates the class
	 */
	public CustomWeapon() {
		
	}
	/**
	 * The {@link CustomWeapon}'s damage value
	 * 
	 * @return The damage value the weapon should do
	 */
	public int getDamage() {
		return 0;
	}
	/**
	 * Called when the a player hits an entity while holding the custom item
	 * 
	 * @param event The {@link EntityDamageEvent} that has occurred
	 * @return If to cancel the event
	 */
	public boolean EntityDamageEvent(EntityDamageEvent event) {return false;}
	
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
				for (Entry<String, Object> entry : getDefaultNBT().entrySet()) 
					addNBTTag(entry.getKey(), entry.getValue().toString(), NBTTagType.STRING);
				for (ItemFlag flag : getItemFlags()) 
					addItemFlag(flag);
				setAttackDamage(getDamage());
				addNBTTag("UUID", getUUID().toString(), NBTTagType.STRING);
				addNBTTag("RARITY", getRarity().getUUID(), NBTTagType.STRING);
				addLoreLine("§fRarity: "+getRarity().getName());
			}}.build();
	}
}
