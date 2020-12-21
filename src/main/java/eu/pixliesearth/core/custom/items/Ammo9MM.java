package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomItem;

public class Ammo9MM extends CustomItem {

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getUUID() {
        return "Ammo:9mm";
    }

    @Override
    public Integer getCustomModelData() {
        return 6;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§39mm Ammo";
    }

}
