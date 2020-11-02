package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.*;
/**
 * 
 * @author BradBot_1
 * 
 * <h3>An item that cannot be interacted with in a {@link Inventory} (Handled by {@link CustomInventoryListener})</h3>
 *
 */
public class ItemUnclickableGlass extends CustomItem {
	
	public ItemUnclickableGlass() {

    }

    @Override
    public Material getMaterial() {
        return Material.BLACK_STAINED_GLASS_PANE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return null;
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>();
    }

    @Override
    public Map<Enchantment, Integer> getDefaultEnchants() {
        return new HashMap<Enchantment, Integer>();
    }

    @Override
    public Set<ItemFlag> getItemFlags(){
        return new HashSet<ItemFlag>();
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.NONE;
    }

    @Override
    public String getUUID() {
        return CustomInventoryListener.getUnclickableItemUUID(); // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
