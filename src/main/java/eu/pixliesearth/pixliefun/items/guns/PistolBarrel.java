package eu.pixliesearth.pixliefun.items.guns;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PistolBarrel extends SlimefunItem {

    public PistolBarrel() {
        super(PixlieFun.gunsCategory, PixlieFunItems.PISTOL_BARREL, PixlieFun.GUN_WORKBENCH, new ItemStack[]{
                SlimefunItems.BRONZE_INGOT, SlimefunItems.LEAD_INGOT,  SlimefunItems.TIN_INGOT.asQuantity(2), new ItemStack(Material.GUNPOWDER), SlimefunItems.LEAD_INGOT, null, null, null, null
        });
    }


}
