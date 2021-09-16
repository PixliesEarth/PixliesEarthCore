package eu.pixliesearth.pixliefun.items.guns;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.inventory.ItemStack;

public class RifleAmmo extends SlimefunItem {

    public RifleAmmo() {
        super(PixlieFun.gunsCategory, PixlieFunItems.RIFLE_BARREL, PixlieFun.GUN_WORKBENCH, new ItemStack[]{
                SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.STEEL_INGOT.asQuantity(2),  null, null, null, null, null, null, null
        });
    }

}
