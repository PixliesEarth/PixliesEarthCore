package eu.pixliesearth.pixliefun.items.traffic;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Speed50 extends SlimefunItem {

    public Speed50() {
        super(PixlieFun.trafficCategory, PixlieFunItems.SPEED_50, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.IRON_INGOT, 3), new ItemStack(Material.GOLD_NUGGET, 2), null,
                null, null, null,
                null, null, null
        });
    }

}