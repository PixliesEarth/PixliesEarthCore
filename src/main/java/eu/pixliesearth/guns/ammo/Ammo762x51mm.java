package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.CustomGun;
import eu.pixliesearth.guns.PixliesAmmo;

public class Ammo762x51mm extends PixliesAmmo {

    public Ammo762x51mm(Location location, CustomGun gun) {
        super(location, gun, 2.0);
    }

    @Override
    public Ammo762x51mm createNewOne(Location location, CustomGun gun) {
        return new Ammo762x51mm(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return CustomItemUtil.getItemStackFromUUID("Ammo:Nato762x51");
    }

}
