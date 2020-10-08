package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
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
    public String getUUID() {
        return CustomInventoryListener.getUnclickableItemUUID(); // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
}
