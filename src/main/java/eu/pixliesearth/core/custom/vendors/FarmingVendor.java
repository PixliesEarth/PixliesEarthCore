package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;

public class FarmingVendor extends Vendor {

    public FarmingVendor() {
        super("§6Zenake the farmer", "§6Zenake the farmer",
                new ItemStack(KELP),
                new ItemStack(HAY_BLOCK),
                new ItemStack(WHEAT_SEEDS),
                new ItemStack(SUGAR_CANE),
                new ItemStack(BAMBOO),
                new ItemStack(PUMPKIN),
                new ItemStack(MELON),
                new ItemStack(CARROT),
                new ItemStack(CHORUS_FRUIT),
                new ItemStack(CACTUS),
                new ItemStack(SLIME_BALL),
                new ItemStack(HONEYCOMB));
    }

}
