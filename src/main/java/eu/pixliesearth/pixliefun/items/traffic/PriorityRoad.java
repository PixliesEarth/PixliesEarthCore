package eu.pixliesearth.pixliefun.items.traffic;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PriorityRoad extends SlimefunItem {

    public PriorityRoad() {
        super(PixlieFun.trafficCategory, PixlieFunItems.PRIORITY_ROAD, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.IRON_INGOT, 3), new ItemStack(Material.GOLD_NUGGET, 4), null,
                null, null, null,
                null, null, null
        });
    }

}
