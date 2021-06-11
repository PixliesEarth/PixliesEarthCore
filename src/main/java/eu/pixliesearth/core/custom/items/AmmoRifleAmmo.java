package eu.pixliesearth.core.custom.items;

import org.bukkit.Material;

import eu.pixliesearth.core.custom.CustomItem;

public class AmmoRifleAmmo extends CustomItem {

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getUUID() {
        return "Ammo:Rifle-Ammo";
    }

    @Override
    public Integer getCustomModelData() {
        return 5;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§aRifle Ammo";
    }

}
