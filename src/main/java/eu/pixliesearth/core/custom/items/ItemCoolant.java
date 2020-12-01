package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class ItemCoolant extends CustomItem {
	
    public ItemCoolant() {

    }

    @Override
    public Material getMaterial() {
        return Material.CYAN_DYE;
    }

    @Override
    public List<String> getDefaultLore() {
        return null;
    }
    
    @Override
    public boolean isUnstackable() {
    	return true;
    }
    
    @Override
    public String getDefaultDisplayName() {
        return "§6Nuclear Coolant";
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Nuclear_Coolant"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return false;
    }
}