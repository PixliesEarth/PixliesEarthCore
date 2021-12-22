package eu.pixliesearth.pixliefun.items.traffic;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Yield extends SlimefunItem {

    public Yield() {
        super(PixlieFun.trafficCategory, PixlieFunItems.YIELD, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.IRON_INGOT, 3), new ItemStack(Material.GOLD_NUGGET, 5), null,
                null, null, null,
                null, null, null
        });
    }

}
