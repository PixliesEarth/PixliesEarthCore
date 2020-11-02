package eu.pixliesearth.core.custom.vendors;

import eu.pixliesearth.core.vendors.Vendor;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;

public class MiningVendor extends Vendor {

    public MiningVendor() {
        super("§6Miner Julio", "§6Miner Julio", CustomItemUtil.getItemStackFromUUID("Minecraft:stone"),
                CustomItemUtil.getItemStackFromUUID("Minecraft:cobblestone"),
                CustomItemUtil.getItemStackFromUUID("Pixlies:Explosive_Pickaxe"),
                new ItemStack(QUARTZ),
                new ItemStack(NETHER_QUARTZ_ORE),
                new ItemStack(NETHER_GOLD_ORE),
                new ItemStack(GOLD_INGOT),
                new ItemStack(IRON_INGOT),
                new ItemStack(COAL),
                new ItemStack(CHARCOAL),
                new ItemStack(DIAMOND),
                new ItemStack(REDSTONE),
                new ItemStack(EMERALD),
                new ItemStack(LAPIS_LAZULI),
                new ItemStack(NETHERITE_INGOT),
                new ItemStack(ANCIENT_DEBRIS),
                g("Pixlies:Copper_Dust"),
                g("Pixlies:Bronze_Dust"),
                g("Pixlies:Arsenic_Bronze_Dust"),
                g("Pixlies:Tin_Dust"),
                g("Pixlies:Steel_Dust"),
                g("Pixlies:Heavy_Steel_Dust"),
                g("Pixlies:Damascus_Steel_Ingot"),
                g("Pixlies:Cast_Iron_Dust"),
                g("Pixlies:Carbon_Steel_Dust"),
                g("Pixlies:Stainless_Steel_Dust"),
                g("Pixlies:Lead_Dust"),
                g("Pixlies:Rubber"),
                g("Pixlies:Aluminium_Dust"),
                g("Pixlies:Titanium_Dust"),
                g("Pixlies:Tungsten_Dust"),
                g("Pixlies:Platinum_Dust"),
                g("Pixlies:Kevlar"),
                g("Pixlies:Carbon_Fiber"),
                g("Pixlies:Hard_Plastic"),
                g("Pixlies:Solder_Dust"),
                g("Pixlies:Zinc_Dust"),
                g("Pixlies:Cobalt_Dust"),
                g("Pixlies:Lithium_Dust"),
                g("Pixlies:Flouride"),
                g("Pixlies:Deuterium"),
                g("Pixlies:Lunar_Dust")
        );
    }

}
