package eu.pixliesearth.core.custom.vendors;

import static org.bukkit.Material.ACACIA_LOG;
import static org.bukkit.Material.ACACIA_PLANKS;
import static org.bukkit.Material.BASALT;
import static org.bukkit.Material.BIRCH_LOG;
import static org.bukkit.Material.BIRCH_PLANKS;
import static org.bukkit.Material.BLACKSTONE;
import static org.bukkit.Material.BLACK_WOOL;
import static org.bukkit.Material.BLUE_WOOL;
import static org.bukkit.Material.BRICKS;
import static org.bukkit.Material.BROWN_WOOL;
import static org.bukkit.Material.CLAY;
import static org.bukkit.Material.COBBLESTONE;
import static org.bukkit.Material.CRIMSON_HYPHAE;
import static org.bukkit.Material.CRIMSON_NYLIUM;
import static org.bukkit.Material.CRIMSON_PLANKS;
import static org.bukkit.Material.CYAN_WOOL;
import static org.bukkit.Material.DARK_OAK_LOG;
import static org.bukkit.Material.DARK_OAK_PLANKS;
import static org.bukkit.Material.DARK_PRISMARINE;
import static org.bukkit.Material.DIRT;
import static org.bukkit.Material.END_STONE;
import static org.bukkit.Material.GILDED_BLACKSTONE;
import static org.bukkit.Material.GLASS;
import static org.bukkit.Material.GRASS_BLOCK;
import static org.bukkit.Material.GRAY_WOOL;
import static org.bukkit.Material.GREEN_WOOL;
import static org.bukkit.Material.JUNGLE_LOG;
import static org.bukkit.Material.JUNGLE_PLANKS;
import static org.bukkit.Material.LIGHT_BLUE_WOOL;
import static org.bukkit.Material.LIGHT_GRAY_WOOL;
import static org.bukkit.Material.LIME_WOOL;
import static org.bukkit.Material.MAGENTA_WOOL;
import static org.bukkit.Material.MAGMA_BLOCK;
import static org.bukkit.Material.MOSSY_COBBLESTONE;
import static org.bukkit.Material.MYCELIUM;
import static org.bukkit.Material.NETHERRACK;
import static org.bukkit.Material.NETHER_BRICKS;
import static org.bukkit.Material.NETHER_WART_BLOCK;
import static org.bukkit.Material.OAK_LOG;
import static org.bukkit.Material.OAK_PLANKS;
import static org.bukkit.Material.ORANGE_WOOL;
import static org.bukkit.Material.PINK_WOOL;
import static org.bukkit.Material.PODZOL;
import static org.bukkit.Material.POLISHED_BASALT;
import static org.bukkit.Material.POLISHED_BLACKSTONE;
import static org.bukkit.Material.PRISMARINE;
import static org.bukkit.Material.PURPLE_WOOL;
import static org.bukkit.Material.PURPUR_BLOCK;
import static org.bukkit.Material.QUARTZ;
import static org.bukkit.Material.RED_SANDSTONE;
import static org.bukkit.Material.RED_WOOL;
import static org.bukkit.Material.SANDSTONE;
import static org.bukkit.Material.SMOOTH_STONE;
import static org.bukkit.Material.SOUL_SAND;
import static org.bukkit.Material.SOUL_SOIL;
import static org.bukkit.Material.SPONGE;
import static org.bukkit.Material.SPRUCE_LOG;
import static org.bukkit.Material.SPRUCE_PLANKS;
import static org.bukkit.Material.STONE;
import static org.bukkit.Material.STONE_BRICKS;
import static org.bukkit.Material.STRIPPED_ACACIA_LOG;
import static org.bukkit.Material.STRIPPED_BIRCH_LOG;
import static org.bukkit.Material.STRIPPED_CRIMSON_HYPHAE;
import static org.bukkit.Material.STRIPPED_DARK_OAK_LOG;
import static org.bukkit.Material.STRIPPED_JUNGLE_LOG;
import static org.bukkit.Material.STRIPPED_OAK_LOG;
import static org.bukkit.Material.STRIPPED_SPRUCE_LOG;
import static org.bukkit.Material.STRIPPED_WARPED_HYPHAE;
import static org.bukkit.Material.TERRACOTTA;
import static org.bukkit.Material.WARPED_HYPHAE;
import static org.bukkit.Material.WARPED_NYLIUM;
import static org.bukkit.Material.WARPED_PLANKS;
import static org.bukkit.Material.WHITE_CONCRETE;
import static org.bukkit.Material.WHITE_CONCRETE_POWDER;
import static org.bukkit.Material.WHITE_WOOL;
import static org.bukkit.Material.YELLOW_WOOL;

import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.core.vendors.Vendor;

public class BuildingVendor extends Vendor {

    //TODO ITEMS
    public BuildingVendor() {
        super("§9Mick the architect", "§9Mick the architect", new ItemStack(DIRT),
                new ItemStack(GRASS_BLOCK),
                new ItemStack(PODZOL),
                new ItemStack(MYCELIUM),
                new ItemStack(CRIMSON_NYLIUM),
                new ItemStack(WARPED_NYLIUM),
                new ItemStack(STRIPPED_ACACIA_LOG),
                new ItemStack(STRIPPED_BIRCH_LOG),
                new ItemStack(STRIPPED_JUNGLE_LOG),
                new ItemStack(STRIPPED_OAK_LOG),
                new ItemStack(STRIPPED_SPRUCE_LOG),
                new ItemStack(STRIPPED_DARK_OAK_LOG),
                new ItemStack(ACACIA_PLANKS),
                new ItemStack(BIRCH_PLANKS),
                new ItemStack(CRIMSON_PLANKS),
                new ItemStack(DARK_OAK_PLANKS),
                new ItemStack(JUNGLE_PLANKS),
                new ItemStack(OAK_PLANKS),
                new ItemStack(SPRUCE_PLANKS),
                new ItemStack(WARPED_PLANKS),
                new ItemStack(ACACIA_LOG),
                new ItemStack(BIRCH_LOG),
                new ItemStack(JUNGLE_LOG),
                new ItemStack(OAK_LOG),
                new ItemStack(SPRUCE_LOG),
                new ItemStack(DARK_OAK_LOG),
                new ItemStack(CRIMSON_HYPHAE),
                new ItemStack(STRIPPED_CRIMSON_HYPHAE),
                new ItemStack(STRIPPED_WARPED_HYPHAE),
                new ItemStack(WARPED_HYPHAE),
                new ItemStack(SPONGE),
                new ItemStack(SANDSTONE),
                new ItemStack(RED_SANDSTONE),
                new ItemStack(BLACK_WOOL),
                new ItemStack(BLUE_WOOL),
                new ItemStack(BROWN_WOOL),
                new ItemStack(CYAN_WOOL),
                new ItemStack(GRAY_WOOL),
                new ItemStack(GREEN_WOOL),
                new ItemStack(LIGHT_BLUE_WOOL),
                new ItemStack(LIGHT_GRAY_WOOL),
                new ItemStack(LIME_WOOL),
                new ItemStack(MAGENTA_WOOL),
                new ItemStack(ORANGE_WOOL),
                new ItemStack(PINK_WOOL),
                new ItemStack(PURPLE_WOOL),
                new ItemStack(RED_WOOL),
                new ItemStack(WHITE_WOOL),
                new ItemStack(YELLOW_WOOL),
                new ItemStack(QUARTZ),
                new ItemStack(BRICKS),
                new ItemStack(STONE_BRICKS),
                new ItemStack(NETHER_BRICKS),
                new ItemStack(PRISMARINE),
                new ItemStack(DARK_PRISMARINE),
                new ItemStack(PURPUR_BLOCK),
                new ItemStack(CLAY),
                new ItemStack(NETHERRACK),
                new ItemStack(SOUL_SAND),
                new ItemStack(SOUL_SOIL),
                new ItemStack(BASALT),
                new ItemStack(POLISHED_BASALT),
                new ItemStack(END_STONE),
                new ItemStack(TERRACOTTA),
                new ItemStack(WHITE_CONCRETE),
                new ItemStack(WHITE_CONCRETE_POWDER),
                new ItemStack(GLASS),
                new ItemStack(MAGMA_BLOCK),
                new ItemStack(NETHER_WART_BLOCK),
                new ItemStack(BLACKSTONE),
                new ItemStack(POLISHED_BLACKSTONE),
                new ItemStack(GILDED_BLACKSTONE),
                new ItemStack(COBBLESTONE),
                new ItemStack(STONE),
                new ItemStack(SMOOTH_STONE),
                new ItemStack(MOSSY_COBBLESTONE)
                );
    }

}
