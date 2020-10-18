package eu.pixliesearth.core.custom.items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;

public class ItemICBMLocationHolder extends CustomItem {
	
	public ItemICBMLocationHolder() {
		
	}
	
	@Override
	public Material getMaterial() {
		return Material.PAPER;
	}

	@Override
	public List<String> getDefaultLore() {
		return null;
	}

	@Override
	public String getDefaultDisplayName() {
		return "§6Location Saver";
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
		return CreativeTabs.TOOLS;
	}
	
	@Override
	public String getUUID() {
		return "Pixlies:ICBM_Location_Holder"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
	}
	
	@Override
	public boolean PlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getPlayer().isSneaking()) {
			Location l = event.getPlayer().getLocation().clone();
			NBTTags tags = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand());
			tags.addTag("x", Integer.toString(l.getBlockX()), NBTTagType.STRING);
			tags.addTag("y", Integer.toString(l.getBlockY()), NBTTagType.STRING);
			tags.addTag("z", Integer.toString(l.getBlockZ()), NBTTagType.STRING);
			NBTUtil.addTagsToItem(event.getPlayer().getInventory().getItemInMainHand(), tags);
			event.getPlayer().sendMessage("§aSet the location to §r"+l.getBlockX()+"§a,§r"+l.getBlockY()+"§a,§r"+l.getBlockZ());
			return false;
		} else {
			NBTTags tags = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand());
			event.getPlayer().sendMessage("§aBound to the location §r"+tags.getString("x")+"§a,§r"+tags.getString("y")+"§a,§r"+tags.getString("z"));
			return false;
		}
	}
}
