package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.guns.PixliesAmmo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AmmoNato762x51 extends CustomItem {

    private static final ItemStack ammoItem = PixliesAmmo.AmmoType.NATO762x51.getAmmo().getItem();

    @Override
    public Material getMaterial() {
        return ammoItem.getType();
    }

    @Override
    public String getUUID() {
        return "Ammo:Nato762x51";
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
