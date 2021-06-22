package eu.pixliesearth.guns.ammo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.pixliesearth.guns.PixliesAmmo;

public class CobbleStoneAmmo extends PixliesAmmo {

    public CobbleStoneAmmo(Location location, CustomGun gun) {
        super(location, gun, 1.0);
    }

    @Override
    public CobbleStoneAmmo createNewOne(Location location, CustomGun gun) {
        return new CobbleStoneAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.COBBLESTONE);
    }

}
