package eu.pixliesearth.pixliefun.items.guns;

import eu.pixliesearth.pixliefun.PixlieFun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RifleAmmo extends SlimefunItem {

    public RifleAmmo() {
        super(PixlieFun.gunsCategory, PixlieFunItems.RIFLE_AMMO, PixlieFun.GUN_WORKBENCH, new ItemStack[]{
                SlimefunItems.BRONZE_INGOT, SlimefunItems.LEAD_INGOT.asQuantity(2),  SlimefunItems.TIN_INGOT.asQuantity(2), new ItemStack(Material.GUNPOWDER), null, null, null, null, null
        });

    }

}
