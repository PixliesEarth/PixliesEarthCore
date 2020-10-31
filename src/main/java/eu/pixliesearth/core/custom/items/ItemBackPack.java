package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.utils.InventoryUtils;
import eu.pixliesearth.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class ItemBackPack extends CustomItem {
	
    public ItemBackPack() {

    }

    @Override
    public Material getMaterial() {
        return Material.BOOK;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6BackPack";
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    public boolean isUnbreakable() {
        return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Map<String, Object> getDefaultNBT() {
        return new HashMap<String, Object>(){/** * */private static final long serialVersionUID = -8592045097249581373L; {
        	JSONObject obj = new JSONObject();
        	for (int i = 0; i < 27; i++) 
        		obj.put(Integer.toString(i), "EMPTY");
        	put("CONTENTS", obj.toString());
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
        return CreativeTabs.MISC;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Backpack"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	Inventory inv = Bukkit.createInventory(null, 3*9, getDefaultDisplayName());
    	String data = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand()).getString("CONTENTS");
    	try {
			InventoryUtils.getInventoryContentsFromJSONObject((JSONObject)new JSONParser().parse(data), inv);
		} catch (ParseException e) {
			event.getPlayer().sendMessage("[ERROR] Invalid content nbt! Please contact an admin.");
		}
    	event.getPlayer().openInventory(inv);
        return false;
    }
}