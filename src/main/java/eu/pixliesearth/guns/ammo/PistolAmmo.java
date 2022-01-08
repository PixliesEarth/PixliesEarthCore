package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.pixliefun.items.PixlieFunItems;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PistolAmmo extends PixliesAmmo {
    public PistolAmmo(Location location, CustomGun gun) {
        super(location, gun, 1.5);
    }

    @Override
    public PistolAmmo createNewOne(Location location, CustomGun gun) {
        return new PistolAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return PixlieFunItems.PISTOL_AMMO;
    }
}
