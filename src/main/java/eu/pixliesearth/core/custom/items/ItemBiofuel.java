package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemBiofuel extends CustomItem {
	
    public ItemBiofuel() {

    }

    @Override
    public Material getMaterial() {
        return Material.REDSTONE;
    }
    
    @Override
    public String getDefaultDisplayName() {
        return "§6Biofuel";
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Biofuel"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return true;
    }
}