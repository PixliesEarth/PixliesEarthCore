package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.utils.NBTTagType;
import eu.pixliesearth.utils.NBTUtil;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.*;

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
		return 2;
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
			tags.addTag("w", l.getWorld().getUID().toString());
			event.getPlayer().getInventory().setItemInMainHand(NBTUtil.addTagsToItem(event.getPlayer().getInventory().getItemInMainHand(), tags));
			event.getPlayer().sendMessage("§7Set the location to §b"+l.getBlockX()+"§7, §b"+l.getBlockY()+"§7, §b"+l.getBlockZ());
			return false;
		} else {
			NBTTags tags = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand());
			event.getPlayer().sendMessage("§7Bound to the location §b"+tags.getString("x")+"§7, §b"+tags.getString("y")+"§7, §b"+tags.getString("z"));
			return false;
		}
	}
}
