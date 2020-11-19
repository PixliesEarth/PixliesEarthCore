package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.pixliesearth.core.custom.CustomItem;

public class ItemOilBucket extends CustomItem {
	
    public ItemOilBucket() {

    }

    @Override
    public Material getMaterial() {
        return Material.BUCKET;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Oil Bucket";
    }

    @Override
    public Integer getCustomModelData() {
        return null;
    }

    @Override
    public String getUUID() {
        return "Pixlies:Oil_Bucket"; // 6bcc41e5-5a09-4955-8756-f06c26d61c4d
    }

    @Override
    public boolean PlayerInteractEvent(PlayerInteractEvent event) {
        return true;
    }
}