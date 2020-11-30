package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomFuel;
import eu.pixliesearth.core.custom.interfaces.IMissileFuel;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class FuelFuelRod extends CustomFuel implements IMissileFuel {
	
    public FuelFuelRod() {

    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_ROD;
    }
    
    @Override
    public long getBurnTime() {
    	return 500l;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Fuel Rod";
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
        return "Pixlies:Fuel_Rod"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }

	@Override
	public int getMissileExplosiveValue() {
		return 1;
	}

	@Override
	public int getMissileRangeValue() {
		return 5;
	}

	@Override
	public int getMissilePlayerDamageValue() {
		return 0;
	}

	@Override
	public int getMissileLaunchTimeValue() {
		return -3;
	}
}