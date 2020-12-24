package eu.pixliesearth.core.custom.items;

import eu.pixliesearth.core.custom.CustomItem;
import eu.pixliesearth.core.custom.interfaces.IConsumable;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class FoodWaffle extends CustomItem implements IConsumable {

    @Override
    public Material getMaterial() {
        return Material.APPLE;
    }

    @Override
    public Integer getCustomModelData() {
        return 2;
    }

    @Override
    public String getUUID() {
        return "Food:Waffle";
    }

    @Override
    public String getDefaultDisplayName() {
        return "§6Waffle";
    }

    @Override
    public boolean PlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        return false;
    }

}
