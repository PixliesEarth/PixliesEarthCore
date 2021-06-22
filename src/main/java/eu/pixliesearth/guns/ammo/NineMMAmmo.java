package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.utils.CustomItemUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.PixliesAmmo;

public class NineMMAmmo extends PixliesAmmo {

    public NineMMAmmo(Location location, CustomGun gun) {
        super(location, gun, 1.5);
    }

    @Override
    public NineMMAmmo createNewOne(Location location, CustomGun gun) {
        return new NineMMAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return CustomItemUtil.getItemStackFromUUID("Ammo:9mm");
    }

}
