package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomEnergyItem;
import eu.pixliesearth.core.custom.listeners.CustomInventoryListener;
import eu.pixliesearth.utils.*;
import eu.pixliesearth.utils.NBTUtil.NBTTags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class EnergyItemRemoteInteractor extends CustomEnergyItem {
	
    public EnergyItemRemoteInteractor() {

    }
    
    public final int locationSaverSlot = 12;
    public final int itemSlot = 14;
    public final int energySlot = 13;
    public final double energyCost = 1000D;
    
    @Override
    public Material getMaterial() {
        return Material.ENDER_EYE;
    }
    
    @Override
	public double getCapacity() {
		return 100000D;
	}

    @Override
    public List<String> getDefaultLore() {
        return new ArrayList<String>() {private static final long serialVersionUID = -3234274418406689465L;{
        	add("§3Shift Right-Click to open the inventory!");
        	}};
    }

    @Override
    public String getDefaultDisplayName() {
        return "§cRemote Interactor";
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
        return new HashMap<String, Object>(){private static final long serialVersionUID = -8592045097249581373L; {
        	JSONObject obj = new JSONObject();
        	String s = InventoryUtils.serialize(CustomItemUtil.getItemStackFromUUID(CustomInventoryListener.getUnclickableItemUUID())); // cache it for speeeed
        	for (int i = 0; i < 27; i++) 
        		if (i == locationSaverSlot || i == itemSlot) 
        			obj.put(Integer.toString(i), "A");
        		else if (i == energySlot) 
        			obj.put(Integer.toString(i), InventoryUtils.serialize(getEnergyItem(null)));
        		else 
        			obj.put(Integer.toString(i), s);
        	put("CONTENTS", obj.toString());
		}};
    }
    
    @Override
    public boolean isUnstackable() {
    	return true;
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
        return "Pixlies:Remote_Interactor"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }
    
    @Override
    public Rarity getRarity() {
    	return Rarity.VERYRARE;
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	if (event.getPlayer().isSneaking()) {
    		Inventory inv = Bukkit.createInventory(null, 3*9, getDefaultDisplayName());
        	String data = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand()).getString("CONTENTS");
        	try {
    			InventoryUtils.getInventoryContentsFromJSONObject((JSONObject)new JSONParser().parse(data), inv);
    		} catch (ParseException e) {
    			event.getPlayer().sendMessage("[ERROR] Invalid content nbt! Please contact an admin.");
    		}
        	inv.setItem(energySlot, getEnergyItem(event.getPlayer().getInventory().getItemInMainHand()));
        	event.getPlayer().openInventory(inv);
    	} else {
    		try {
    			if (!(getContainedPower(event.getPlayer().getInventory().getItemInMainHand())>=energyCost)) {
    				event.getPlayer().sendMessage("§cYou do not have the energy required for this!");
    				return true;
    			}
    			Inventory inv = Bukkit.createInventory(null, 3*9, getDefaultDisplayName());
    			String data = NBTUtil.getTagsFromItem(event.getPlayer().getInventory().getItemInMainHand()).getString("CONTENTS");
    			InventoryUtils.getInventoryContentsFromJSONObject((JSONObject)new JSONParser().parse(data), inv);
    			ItemStack is = inv.getItem(locationSaverSlot);
    			if (is==null || is.getType().equals(Material.AIR)) {
    				event.getPlayer().sendMessage("§cNo location saver found!");
    				return true;
    			}
    			NBTTags tags = NBTUtil.getTagsFromItem(is);
    			Location l = new Location(Bukkit.getWorld(UUID.fromString(tags.getString("w"))), Integer.parseInt(tags.getString("x")), Integer.parseInt(tags.getString("y")), Integer.parseInt(tags.getString("z")));
	    		Event event2 = new PlayerInteractEvent(event.getPlayer(), Action.RIGHT_CLICK_BLOCK, inv.getItem(itemSlot), l.getBlock(), BlockFace.UP);
	    		event2.callEvent();
	    		event.getPlayer().getInventory().setItemInMainHand(removeEnergy(event.getPlayer().getInventory().getItemInMainHand(), energyCost));
    		} catch (Exception e) {
    			event.getPlayer().sendMessage("§cAn unknown exception occured.");
    		}
    	}
    	return true;
    }
    
    public ItemStack getEnergyItem(ItemStack is) {
    	return new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§6Energy").addLoreLine("§eContained: "+ ((is==null) ? "None": Methods.convertEnergyDouble(Methods.round(getContainedPower(is), 3)))).addLoreLine("§eCapacity: "+Methods.convertEnergyDouble(getCapacity())).addNBTTag("UUID", CustomInventoryListener.getUnclickableItemUUID(), NBTTagType.STRING).build();
    }
    
}