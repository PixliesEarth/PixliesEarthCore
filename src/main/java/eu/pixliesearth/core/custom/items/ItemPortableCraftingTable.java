package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.pixliesearth.core.custom.CustomItem;

public class ItemPortableCraftingTable extends CustomItem {
	
    public ItemPortableCraftingTable() {

    }

    @Override
    public Material getMaterial() {
        return Material.CRAFTING_TABLE;
    }
    
    @Override
    public boolean isGlowing() {
    	return true;
    }
    
    @Override
    public String getDefaultDisplayName() {
        return "§6Portable Crafting Table";
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Portable_Crafting_Table"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
    	event.getPlayer().openWorkbench(null, true);
        return true;
    }
}