package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.guns.PixliesAmmo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AmmoRifleAmmo extends CustomItem {

    private static final ItemStack ammoItem = PixliesAmmo.AmmoType.RIFLE_AMMO.getAmmo().getItem();

    @Override
    public Material getMaterial() {
        return ammoItem.getType();
    }

    @Override
    public String getUUID() {
        return "Ammo:Rifle-Ammo";
    }

    @Override
    public Integer getCustomModelData() {
        return ammoItem.getCustomModelData();
    }

    @Override
    public String getDefaultDisplayName() {
        return ammoItem.getDisplayName();
    }

}
