package eu.pixliesearth.pixliefun.items.guns;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public class RifleBarrel extends SlimefunItem {

    public RifleBarrel() {
        super(PixlieFun.gunsCategory, PixlieFunItems.RIFLE_BARREL, PixlieFun.GUN_WORKBENCH, new ItemStack[]{
                SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.STEEL_INGOT.asQuantity(2),  null, null, null, null, null, null, null
        });
    }

}
