package eu.pixliesearth.core.custom.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.IMissileFuel;

public class ItemEnergyCore extends CustomItem implements IMissileFuel {
	
    public ItemEnergyCore() {

    }

    @Override
    public Material getMaterial() {
        return Material.NETHER_STAR;
    }

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<String>() {/** */private static final long serialVersionUID = 7541112248281122525L;{
        	add("§c§lExtremely explosive!");
        }};
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Unprotected Energy Core";
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
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.MISC;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Energy_Core_Unprotected"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }

	@Override
	public int getMissileExplosiveValue() {
		return 9;
	}

	@Override
	public int getMissileRangeValue() {
		return 12;
	}

	@Override
	public int getMissilePlayerDamageValue() {
		return 35;
	}

	@Override
	public int getMissileLaunchTimeValue() {
		return 15;
	}
}