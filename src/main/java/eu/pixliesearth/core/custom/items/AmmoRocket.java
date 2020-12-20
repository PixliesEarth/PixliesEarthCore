package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.guns.PixliesAmmo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
