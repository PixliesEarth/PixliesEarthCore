package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import org.bukkit.Location;

public class RifleAmmo extends PixliesAmmo {

    public RifleAmmo(Location location, PixliesGun gun) {
        super(location, gun, 3.0);
    }

    @Override
    public RifleAmmo createNewOne(Location location, PixliesGun gun) {
        return new RifleAmmo(location, gun);
    }

}
