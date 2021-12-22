package eu.pixliesearth.pixliefun.items.traffic;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ParkingSign extends SlimefunItem {

    public ParkingSign() {
        super(PixlieFun.trafficCategory, PixlieFunItems.PARKING_SIGN, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.IRON_INGOT, 3), new ItemStack(Material.GOLD_NUGGET, 7), null,
                null, null, null,
                null, null, null
        });
    }

}