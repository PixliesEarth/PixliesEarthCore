package eu.pixliesearth.pixliefun.items.guns;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

public class RifleReceiver extends SlimefunItem {

    public RifleReceiver() {
        super(PixlieFun.gunsCategory, PixlieFunItems.RIFLE_RECEIVER, PixlieFun.GUN_WORKBENCH, new ItemStack[]{
                SlimefunItems.PLASTIC_SHEET, SlimefunItems.DAMASCUS_STEEL_INGOT,  SlimefunItems.STEEL_INGOT, SlimefunItems.LEAD_INGOT, null, null, null, null, null
        });
    }

}
