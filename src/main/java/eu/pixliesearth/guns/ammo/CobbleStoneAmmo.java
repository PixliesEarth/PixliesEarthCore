package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CobbleStoneAmmo extends PixliesAmmo {

    public CobbleStoneAmmo(Location location, PixliesGun gun) {
        super(location, gun, 1.0);
    }

    @Override
    public CobbleStoneAmmo createNewOne(Location location, PixliesGun gun) {
        return new CobbleStoneAmmo(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.COBBLESTONE);
    }

}
