package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import org.bukkit.Material;

public class FoodFlour extends CustomItem {

    @Override
    public Material getMaterial() {
        return Material.SUGAR;
    }

    @Override
    public String getUUID() {
        return "Food:Flour";
    }

    @Override
    public Integer getCustomModelData() {
        return 2;
    }
}
