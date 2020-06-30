package eu.pixliesearth.core.customitems;

import eu.pixliesearth.core.customitems.ci.ItemExplosivePick;
import eu.pixliesearth.core.customitems.ci.ItemExplosiveShovel;
import eu.pixliesearth.core.customitems.ci.ItemSlingshot;
import eu.pixliesearth.core.customitems.ci.ItemStoneHatchet;

public enum CustomItems {

    SLINGSHOT(new ItemSlingshot()),
    EXPLOSIVE_PICKAXE(new ItemExplosivePick()),
    EXPLOSIVE_SHOVEL(new ItemExplosiveShovel()),
    STONE_HATCHET(new ItemStoneHatchet());

    public CustomItem clazz;

    CustomItems(CustomItem clazz) {
        this.clazz = clazz;
    }

    public static boolean contains(String test) {
        for (CustomItems c : values()) {
            if (c.name().equals(test))
                return true;
        }
        return false;
    }

}