package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.PixliesAmmo;

public class RifleAmmo extends PixliesAmmo {

    public RifleAmmo(Location location, CustomGun gun) {
        super(location, gun, 2.5);
    }

    @Override
    public RifleAmmo createNewOne(Location location, CustomGun gun) {
        return new RifleAmmo(location, gun);
    }
    
    @Override
    public ItemStack getItem() {
        return PixlieFunItems.RIFLE_AMMO;
    }

}
