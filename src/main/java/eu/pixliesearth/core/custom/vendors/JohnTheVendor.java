package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;

public class JohnTheVendor extends Vendor {

    public JohnTheVendor() {
        super("§bJohn the vendor", "§bJohn the vendor",
                new ItemStack(DIAMOND),
                new ItemStack(NETHERITE_SCRAP),
                new ItemStack(SHULKER_SHELL),
                new ItemStack(GRAVEL),
                new ItemStack(SAND),
                new ItemStack(QUARTZ),
                new ItemStack(PRISMARINE_SHARD),
                new ItemStack(PRISMARINE_CRYSTALS),
                new ItemStack(REDSTONE),
                new ItemStack(OBSERVER),
                new ItemStack(DISPENSER),
                g("Pixlies:Explosive_Pickaxe"),
                g("Pixlies:Uranium_Chunk"),
                new ItemStack(GOLD_INGOT),
                new ItemStack(COOKED_BEEF)
                );
    }

}
