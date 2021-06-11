package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomWeapon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class WeaponBronzeSword extends CustomWeapon {
	
	public WeaponBronzeSword() {
		
	}
	
	@Override
	public Material getMaterial() {
		return Material.GOLDEN_SWORD;
	}

	@Override
	public List<String> getDefaultLore() {
		return null;
	}

	@Override
	public String getDefaultDisplayName() {
		return "§6Bronze Sword";
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
		return 11;
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.REDSTONE;
	}
	
	@Override
	public String getUUID() {
		return "Pixlies:Bronze_Sword"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
	}
	
	@Override
	public int getDamage() {
		return 6;
	}
	
	@Override
	public boolean PlayerInteractEvent(PlayerInteractEvent event) {
		return false;
	}

}
