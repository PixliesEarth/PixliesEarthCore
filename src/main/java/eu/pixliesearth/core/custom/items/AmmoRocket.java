package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomItem;

public class AmmoRocket extends CustomItem {

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getUUID() {
        return "Ammo:Rocket";
    }

    @Override
    public Integer getCustomModelData() {
        return 9;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§3Rocket";
    }

}
