package eu.pixliesearth.guns.ammo;

import eu.pixliesearth.guns.PixliesAmmo;
import eu.pixliesearth.guns.PixliesGun;
import eu.pixliesearth.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Ammo762x51mm extends PixliesAmmo {

    public Ammo762x51mm(Location location, PixliesGun gun) {
        super(location, gun, 2.0);
    }

    @Override
    public Ammo762x51mm createNewOne(Location location, PixliesGun gun) {
        return new Ammo762x51mm(location, gun);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).setDisplayName("§37.62x51mm NATO").setCustomModelData(7).build();
    }

}
