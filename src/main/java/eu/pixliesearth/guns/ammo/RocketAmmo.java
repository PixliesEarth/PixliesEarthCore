package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.PixliesAmmo;

public class RocketAmmo extends PixliesAmmo {

    public RocketAmmo(Location location, CustomGun gun) {
        super(location, gun, 3.0);
    }

    @Override
    public RocketAmmo createNewOne(Location location, CustomGun gun) {
        return new RocketAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return CustomItemUtil.getItemStackFromUUID("Ammo:Rocket");
    }

}
