package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.IConsumable;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodIceCreamCone extends CustomItem implements IConsumable {

    @Override
    public Material getMaterial() {
        return Material.PUMPKIN_PIE;
    }

    @Override
    public String getUUID() {
        return "Food:IceCreamCone";
    }

    @Override
    public Integer getCustomModelData() {
        return 2;
    }

    @Override
    public String getDefaultDisplayName() {
        return "§bIce Cream Cone";
    }

    @Override
    public boolean PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        return false;
    }

}
