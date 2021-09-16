package eu.pixliesearth.pixliefun.items.guns;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

public class PistolReceiver extends SlimefunItem {

    public PistolReceiver() {
        super(PixlieFun.gunsCategory, PixlieFunItems.PISTOL_RECEIVER, PixlieFun.GUN_WORKBENCH, new ItemStack[]{
                SlimefunItems.PLASTIC_SHEET, SlimefunItems.DAMASCUS_STEEL_INGOT.asQuantity(2),  SlimefunItems.STEEL_INGOT, SlimefunItems.LEAD_INGOT, null, null, null, null, null
        });
    }

}
