package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;

public class WoodVendor extends Vendor {

    //TODO ITEMS
    public WoodVendor() {
        super("§cGroot the lumberjack", "§cGroot the lumberjack",
                new ItemStack(STRIPPED_ACACIA_LOG),
                new ItemStack(STRIPPED_BIRCH_LOG),
                new ItemStack(STRIPPED_JUNGLE_LOG),
                new ItemStack(STRIPPED_OAK_LOG),
                new ItemStack(STRIPPED_SPRUCE_LOG),
                new ItemStack(STRIPPED_DARK_OAK_LOG),
                new ItemStack(ACACIA_LOG),
                new ItemStack(BIRCH_LOG),
                new ItemStack(JUNGLE_LOG),
                new ItemStack(OAK_LOG),
                new ItemStack(SPRUCE_LOG),
                new ItemStack(DARK_OAK_LOG),
                new ItemStack(CRIMSON_HYPHAE),
                new ItemStack(STRIPPED_CRIMSON_HYPHAE),
                new ItemStack(STRIPPED_WARPED_HYPHAE),
                new ItemStack(WARPED_HYPHAE));
    }

}
