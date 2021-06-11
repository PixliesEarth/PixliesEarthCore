package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

public class ItemICBMCard extends CustomItem {
	
	public ItemICBMCard() {
		
	}
	
	@Override
	public Material getMaterial() {
		return Material.PAPER;
	}

	@Override
	public List<String> getDefaultLore() {
		return new ArrayList<String>() {/** * */private static final long serialVersionUID = 8448957107219658883L;{
				add("§aGiven out at events!");
				add("§aPut me in a icbm to get a custom missile!");
		}};
	}

	@Override
	public String getDefaultDisplayName() {
		return "§6ICBM Card";
	}

	@Override
	public boolean isGlowing() {
		return true;
	}

	@Override
	public boolean isUnbreakable() {
		return false;
	}

	@Override
	public Map<String, Object> getDefaultNBT() {
		return new HashMap<String, Object>(){/** * */private static final long serialVersionUID = -8448957107219658883L;{
			put("e", "1");
			put("d", "1");
			put("r", "1");
			put("l", "1");
		}};
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
		return CreativeTabs.NONE;
	}
	
	@Override
	public String getUUID() {
		return "Pixlies:ICBM_Card"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
	}
	
	@Override
	public Rarity getRarity() {
		return Rarity.LEGENDARY;
	}
	
	@Override
	public boolean PlayerInteractEvent(PlayerInteractEvent event) {
		return false;
	}
}
