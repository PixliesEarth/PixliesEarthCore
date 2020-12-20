package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.guns.PixliesAmmo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
