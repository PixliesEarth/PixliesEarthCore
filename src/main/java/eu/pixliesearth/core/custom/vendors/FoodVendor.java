package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;

public class FoodVendor extends Vendor {

    //TODO ITEMS
    public FoodVendor() {
        super("§bBrad the grocer", "§bBrad the grocer",
                new ItemStack(COOKIE),
                new ItemStack(WHEAT),
                new ItemStack(APPLE),
                new ItemStack(BREAD),
                new ItemStack(GOLDEN_APPLE));
    }

}
