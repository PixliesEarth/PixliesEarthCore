package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;

public class AmmoNato762x51 extends CustomItem {

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public String getUUID() {
        return "Ammo:Nato762x51";
    }

    @Override
    public Integer getCustomModelData() {
        return 7;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§37.62x51mm NATO";
    }

}
