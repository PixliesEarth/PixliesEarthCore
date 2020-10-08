package eu.pixliesearth.core.custom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.utils.ItemBuilder;
import eu.pixliesearth.utils.NBTTagType;
import lombok.Getter;

/**
 * 
 * @author BradBot_1
 * 
 * <h3>The base of a custom item!</h3>
 *
 * @apiNote To create a custom item extend this class then register it
 * 
 * @apiNote TODO: notes
 * 
 */
public class CustomItem {
	
	public CustomItem() {
		
	}
	
	public Material getMaterial() {
		return null;
	}
	
	public List<String> getDefaultLore() {
		return null;
	}
	
	public String getDefaultDisplayName() {
		return null;
	}
	
	public boolean isGlowing() {
		return false;
	}
	
	public boolean isUnbreakable() {
		return false;
	}
	
	public Map<String, Object> getDefaultNBT() {
		return new HashMap<String, Object>();
	}
	
	public Map<Enchantment, Integer> getDefaultEnchants() {
		return new HashMap<Enchantment, Integer>();
	}
	
	public Set<ItemFlag> getItemFlags(){
		return new HashSet<ItemFlag>();
	}
	
	public Integer getCustomModelData() {
		return null;
	}
	
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.NONE;
	}
	
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
				addNBTTag("UUID", getUUID().toString(), NBTTagType.STRING);
			}}.build();
	}
	
	/**
	 * Called when the item is interacted with by a player
	 * 
	 * @param event the {@link PlayerInteractEvent} that this items is associated with
	 * 
	 * @return If the {@link PlayerInteractEvent} should be cancelled
	 */
	public boolean PlayerInteractEvent(PlayerInteractEvent event) {return false;}
	/**
	 * This is not a per item id, it is the item type id!
	 * For example all Copper ingots should have this id!
	 * 
	 * @apiNote this id is used for telling different custom items apart!
	 * 
	 * @return The item type id
	 */
	public String getUUID() {
		return null;
	}
	/**
	 * 
	 * @author BradBot_1
	 * 
	 * <h3>An enum containing all creative tabs</h3>
	 *
	 * @apiNote Not all of these are implemented yet!
	 */
	public static enum CreativeTabs {
		NONE("", ""),
		BUILDING("b", "Building Blocks"),
		BREWING("k", "Brewing"),
		COMBAT("j", "Combat"),
		DECORATIONS("c", "Decoration Blocks"),
		FOOD("h", "Foodstuffs"),
		MISC("f", "Miscellaneous"),
		MISCELLANEOUS("l", "Miscellaneous"),
		REDSTONE("d", "Redstone"),
		TOOLS("i", "Tools"),
		TRANSPORTATION("e", "Transportation"),
		// Not really ment to be used
		SEARCH("g", "Search"),
		HOTBAR("m", ""),
		INVENTORY("n", ""),
		;
		
		protected @Getter String id;
		protected @Getter String name;
		
		CreativeTabs(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public static CreativeTabs getFromID(String id) {
			for (CreativeTabs c : values()) 
				if (c.getId().equals(id)) 
					return c;
			return CreativeTabs.NONE;
		}
		
		public static CreativeTabs getFromName(String name) {
			for (CreativeTabs c : values()) 
				if (c.getName().equals(name)) 
					return c;
			return CreativeTabs.NONE;
		}
	}
}
